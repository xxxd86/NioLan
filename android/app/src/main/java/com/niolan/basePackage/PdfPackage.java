package com.niolan.basePackage;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.niolan.viewmanager.RCTPdfM;
import com.niolan.viewmanager.RCTPdfManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PdfPackage implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactApplicationContext) {
        return Arrays.<ViewManager>asList(
                new RCTPdfM(reactApplicationContext)
        );
    }
}
