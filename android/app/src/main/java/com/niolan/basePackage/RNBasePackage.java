package com.niolan.basePackage;


import androidx.annotation.NonNull;

//import com.example.demo.reactnative.base.module.RNBaseReactModule;
//import com.example.demo.reactnative.base.module.StorageModule;
//import com.example.demo.reactnative.base.module.ToastModule;
//import com.example.demo.reactnative.base.viewManager.MyTextviewManager;
//import com.example.demo.reactnative.base.viewManager.MyWebViewManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by brett.li
 * on 2022/10/17
 */
public class RNBasePackage implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        //modules.add(RNBaseReactModule.getInstance(reactContext).setReactApplicationContext(reactContext));
        //      modules.add(new ToastModule(reactContext));
        //    modules.add(new StorageModule(reactContext));
        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        List<ViewManager> modules = new ArrayList<>();
        modules.add(new MyTextviewManager(reactContext));
        return modules;
    }
}

