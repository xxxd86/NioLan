/**
 * @format
 */

import {
  Alert,
  AppRegistry,
  Button,
  Image,
  Platform,
  requireNativeComponent,
} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import {StyleSheet, Text, View} from 'react-native';
// import ImageView from './ImageView';
import PdfView from './pdfview';
// import ReactECharts from 'echarts-for-react';

const HelloWorld = () => {
  return (
    <View style={styles.container}>
      {/* <Text>你好</Text>
      <ImageView
        style={{height: 300, width: 300}}
        resizeMode={'cover'}
        src={[
          {
            uri: 'https://copyright.bdstatic.com/vcg/creative/cc9c744cf9f7c864889c563cbdeddce6.jpg@h_1280',
          },
        ]}
      />
      <Text>你好</Text> */}
      <PdfView url="https://www.baidu.com/" style={styles.container} />
      {/* <AppCharts /> */}
    </View>
  );
};

//1.添加该代码，确保rn端能够找到该原生ui
// const RCTTextView =
//   Platform.OS === 'android' ? requireNativeComponent('RCTImageView') : null;
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent('hello', () => HelloWorld);
export default HelloWorld;
