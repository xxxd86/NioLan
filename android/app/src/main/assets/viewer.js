/* Copyright 2016 Mozilla Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

"use strict";

if (!pdfjsLib.getDocument || !pdfjsViewer.PDFViewer) {
  alert(" pdfjs 缺失");
}

var USE_ONLY_CSS_ZOOM = true;
var TEXT_LAYER_MODE = 0; // DISABLE
var CMAP_URL = "pdfjs-dist/cmaps/";
// var CMAP_URL = "https://unpkg.com/pdfjs-dist@2.0.943/cmaps/";
var CMAP_PACKED = true;

pdfjsLib.GlobalWorkerOptions.workerSrc =
  "pdfjs-dist/build/pdf.worker.js";

var DEFAULT_URL = "demo.pdf";

function getQueryVariable(variable) {
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split("=");
    if (pair[0] == variable) {
      return pair[1];
    }
  }
  return (false);
}

if (getQueryVariable("file")) {
  DEFAULT_URL = getQueryVariable("file")
  console.log("pdf->" + DEFAULT_URL)
}

var DEFAULT_SCALE_DELTA = 1.1;
var MIN_SCALE = 0.25;
var MAX_SCALE = 10.0;
var DEFAULT_SCALE_VALUE = "auto";

var PDFViewerApplication = {
  pdfLoadingTask: null,
  pdfDocument: null,
  pdfViewer: null,
  pdfHistory: null,
  pdfLinkService: null,
  eventBus: null,

  /**
   * Opens PDF document specified by URL.
   * @returns {Promise} - Returns the promise, which is resolved when document
   *                      is opened.
   */
  open: function(params) {
    if (this.pdfLoadingTask) {
      // We need to destroy already opened document
      return this.close().then(
        function() {
          // ... and repeat the open() call.
          return this.open(params);
        }.bind(this)
      );
    }

    var url = params.url;
    var self = this;

    /*    cMapUrl Adobe的字体库
    cMapPacked 指定Adobe CMap是否用二进制打包。 */

    // 加载 PDF 文档，
    var loadingTask = pdfjsLib.getDocument({
      url: url,
      cMapUrl: CMAP_URL,
      cMapPacked: CMAP_PACKED,
    });
    this.pdfLoadingTask = loadingTask;

    loadingTask.onProgress = function(progressData) {
      self.progress(progressData.loaded / progressData.total);
    };

    return loadingTask.promise.then(
      function(pdfDocument) {
        // 文档已经加载，为文档指定显示器
        self.pdfDocument = pdfDocument;
        self.pdfViewer.setDocument(pdfDocument);
        self.pdfLinkService.setDocument(pdfDocument);
        self.pdfHistory.initialize({ fingerprint: pdfDocument.fingerprint });

        self.loadingBar.hide();
        self.setTitleUsingMetadata(pdfDocument);
      },
      function(exception) {
        var message = exception && exception.message;
        var l10n = self.l10n;
        var loadingErrorMessage;

        if (exception instanceof pdfjsLib.InvalidPDFException) {
          // change error message also for other builds
          loadingErrorMessage = l10n.get(
            "invalid_file_error",
            null,
            "Invalid or corrupted PDF file."
          );
        } else if (exception instanceof pdfjsLib.MissingPDFException) {
          // special message for missing PDFs
          loadingErrorMessage = l10n.get(
            "missing_file_error",
            null,
            "Missing PDF file."
          );
        } else if (exception instanceof pdfjsLib.UnexpectedResponseException) {
          loadingErrorMessage = l10n.get(
            "unexpected_response_error",
            null,
            "Unexpected server response."
          );
        } else {
          loadingErrorMessage = l10n.get(
            "loading_error",
            null,
            "An error occurred while loading the PDF."
          );
        }

        loadingErrorMessage.then(function(msg) {
          self.error(msg, { message: message });
        });
        self.loadingBar.hide();
      }
    );
  },

  /**
   * Closes opened PDF document.
   * @returns {Promise} - Returns the promise, which is resolved when all
   *                      destruction is completed.
   */
  close: function() {
    var errorWrapper = document.getElementById("errorWrapper");
    errorWrapper.setAttribute("hidden", "true");

    if (!this.pdfLoadingTask) {
      return Promise.resolve();
    }

    var promise = this.pdfLoadingTask.destroy();
    this.pdfLoadingTask = null;

    if (this.pdfDocument) {
      this.pdfDocument = null;

      this.pdfViewer.setDocument(null);
      this.pdfLinkService.setDocument(null, null);

      if (this.pdfHistory) {
        this.pdfHistory.reset();
      }
    }

    return promise;
  },

  get loadingBar() {
    var bar = new pdfjsViewer.ProgressBar("#loadingBar", {});

    return pdfjsLib.shadow(this, "loadingBar", bar);
  },



  setTitleUsingMetadata: function(pdfDocument) {
    var self = this;
    pdfDocument.getMetadata().then(function(data) {
      var info = data.info,
        metadata = data.metadata;
      self.documentInfo = info;
      self.metadata = metadata;

      // Provides some basic debug information
      console.log(
        "PDF " +
          pdfDocument.fingerprint +
          " [" +
          info.PDFFormatVersion +
          " " +
          (info.Producer || "-").trim() +
          " / " +
          (info.Creator || "-").trim() +
          "]" +
          " (PDF.js: " +
          (pdfjsLib.version || "-") +
          ")"
      );

      var pdfTitle;
      if (metadata && metadata.has("dc:title")) {
        var title = metadata.get("dc:title");
        // Ghostscript sometimes returns 'Untitled', so prevent setting the
        // title to 'Untitled.
        if (title !== "Untitled") {
          pdfTitle = title;
        }
      }

      if (!pdfTitle && info && info["Title"]) {
        pdfTitle = info["Title"];
      }


    });
  },


  error: function pdfViewError(message, moreInfo) {
    var l10n = this.l10n;
    var moreInfoText = [
      l10n.get(
        "error_version_info",
        { version: pdfjsLib.version || "?", build: pdfjsLib.build || "?" },
        "PDF.js v{{version}} (build: {{build}})"
      ),
    ];

    if (moreInfo) {
      moreInfoText.push(
        l10n.get(
          "error_message",
          { message: moreInfo.message },
          "Message: {{message}}"
        )
      );
      if (moreInfo.stack) {
        moreInfoText.push(
          l10n.get("error_stack", { stack: moreInfo.stack }, "Stack: {{stack}}")
        );
      } else {
        if (moreInfo.filename) {
          moreInfoText.push(
            l10n.get(
              "error_file",
              { file: moreInfo.filename },
              "File: {{file}}"
            )
          );
        }
        if (moreInfo.lineNumber) {
          moreInfoText.push(
            l10n.get(
              "error_line",
              { line: moreInfo.lineNumber },
              "Line: {{line}}"
            )
          );
        }
      }
    }

    var errorWrapper = document.getElementById("errorWrapper");
    errorWrapper.removeAttribute("hidden");

    var errorMessage = document.getElementById("errorMessage");
    errorMessage.textContent = message;

    var closeButton = document.getElementById("errorClose");
    closeButton.onclick = function() {
      errorWrapper.setAttribute("hidden", "true");
    };

    var errorMoreInfo = document.getElementById("errorMoreInfo");
    var moreInfoButton = document.getElementById("errorShowMore");
    var lessInfoButton = document.getElementById("errorShowLess");
    moreInfoButton.onclick = function() {
      errorMoreInfo.removeAttribute("hidden");
      moreInfoButton.setAttribute("hidden", "true");
      lessInfoButton.removeAttribute("hidden");
      errorMoreInfo.style.height = errorMoreInfo.scrollHeight + "px";
    };
    lessInfoButton.onclick = function() {
      errorMoreInfo.setAttribute("hidden", "true");
      moreInfoButton.removeAttribute("hidden");
      lessInfoButton.setAttribute("hidden", "true");
    };
    moreInfoButton.removeAttribute("hidden");
    lessInfoButton.setAttribute("hidden", "true");
    Promise.all(moreInfoText).then(function(parts) {
      errorMoreInfo.value = parts.join("\n");
    });
  },

  progress: function pdfViewProgress(level) {
    var percent = Math.round(level * 100);
    // Updating the bar if value increases.
    if (percent > this.loadingBar.percent || isNaN(percent)) {
      this.loadingBar.percent = percent;
    }
  },

  get pagesCount() {
    return this.pdfDocument.numPages;
  },

  set page(val) {
    this.pdfViewer.currentPageNumber = val;
  },

  get page() {
    return this.pdfViewer.currentPageNumber;
  },

  zoomIn: function pdfViewZoomIn(ticks) {
    var newScale = this.pdfViewer.currentScale;
    do {
      newScale = (newScale * DEFAULT_SCALE_DELTA).toFixed(2);
      newScale = Math.ceil(newScale * 10) / 10;
      newScale = Math.min(MAX_SCALE, newScale);
    } while (--ticks && newScale < MAX_SCALE);
    this.pdfViewer.currentScaleValue = newScale;
  },

  zoomOut: function pdfViewZoomOut(ticks) {
    var newScale = this.pdfViewer.currentScale;
    do {
      newScale = (newScale / DEFAULT_SCALE_DELTA).toFixed(2);
      newScale = Math.floor(newScale * 10) / 10;
      newScale = Math.max(MIN_SCALE, newScale);
    } while (--ticks && newScale > MIN_SCALE);
    this.pdfViewer.currentScaleValue = newScale;
  },

  initUI: function pdfViewInitUI() {
    var eventBus = new pdfjsViewer.EventBus();
    this.eventBus = eventBus;

    var linkService = new pdfjsViewer.PDFLinkService({
      eventBus: eventBus,
    });
    this.pdfLinkService = linkService;

    this.l10n = pdfjsViewer.NullL10n;

    var container = document.getElementById("viewerContainer");
    var pdfViewer = new pdfjsViewer.PDFViewer({
      container: container,
      eventBus: eventBus,
      linkService: linkService,
      l10n: this.l10n,
      useOnlyCssZoom: USE_ONLY_CSS_ZOOM,
      textLayerMode: TEXT_LAYER_MODE,
    });
    this.pdfViewer = pdfViewer;
    linkService.setViewer(pdfViewer);

    this.pdfHistory = new pdfjsViewer.PDFHistory({
      eventBus: eventBus,
      linkService: linkService,
    });
    linkService.setHistory(this.pdfHistory);




    eventBus.on("pagesinit", function() {
      // We can use pdfViewer now, e.g. let's change default scale.
      pdfViewer.currentScaleValue = DEFAULT_SCALE_VALUE;
    });

    eventBus.on(
      "pagechanging",
      function(evt) {
        var page = evt.pageNumber;
        var numPages = PDFViewerApplication.pagesCount;
        console.log("pagechanging-->"+page)
         document.getElementById("pageNumber").value = page;
         document.getElementById("previous").disabled = page <= 1;
         document.getElementById("next").disabled = page >= numPages;
      },
      true
    );
  },
};

document.addEventListener(
  "DOMContentLoaded",
  function() {
    PDFViewerApplication.initUI();
  },
  true
);

(function animationStartedClosure() {
  // The offsetParent is not set until the PDF.js iframe or object is visible.
  // Waiting for first animation.
  PDFViewerApplication.animationStartedPromise = new Promise(function(resolve) {
    window.requestAnimationFrame(resolve);
  });
})();

// We need to delay opening until all HTML is loaded.
PDFViewerApplication.animationStartedPromise.then(function() {
  PDFViewerApplication.open({
    url: DEFAULT_URL,
  });
});
