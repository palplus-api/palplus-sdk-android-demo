package me.palplus.demo;

import android.app.Application;
import me.palplus.sdk.PalPlus;

public class DemoApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    PalPlus.init(this);
  }
}
