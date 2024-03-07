/**
 * @format
 */

import {Alert, AppRegistry, Button} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import {StyleSheet, Text, View} from 'react-native';
import ToastExample from './ToastExample';
const HelloWorld = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.hello}>这个世界太美好了</Text>
      <Button
        style={styles.hello}
        onPress={() => {
          ToastExample.show('Awesome', ToastExample.SHORT);
        }}
        title="点我！"
      />
    </View>
  );
};
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
