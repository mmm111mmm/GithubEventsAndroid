package com.newfivefour.githubevents;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class App extends Application {
  public static App sApp;

  @Override public void onCreate() {
    super.onCreate();
    sApp = this;
  }

  public static boolean isNetworkAvailable() {
    ConnectivityManager cm = (ConnectivityManager) sApp.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnectedOrConnecting();
  }
}
