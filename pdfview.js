import PropTypes from 'prop-types';
import {requireNativeComponent, ViewPropTypes} from 'react-native';

var iface = {
  name: 'PdfView',
  propTypes: {
    url: PropTypes.string,
  },
};

module.exports = requireNativeComponent('RCTPDFVIEWER', iface);
