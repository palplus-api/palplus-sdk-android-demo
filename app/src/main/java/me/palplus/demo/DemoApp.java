package me.palplus.demo;

import android.app.Application;
import me.palplus.sdk.PalPlus;

public class DemoApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    // consult palplus developer console for appKey
    // https://console.palplus.me/developer
    String appKey = "494c81669a4d3cccb906";

    PalPlus.init(this, appKey);

    // If app does not use any Messenger/Connect feature, App key is optional
    // PalPlus.init(this, null);
  }
}
