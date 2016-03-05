package com.newfivefour.githubevents.logique;

import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Func1;

public class AppStateMaps {
  static Func1<AppState, AppState> setErrorOnMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setError(parseException(appState.getException()));
      return appState;
    }
  };
  static Func1<AppState, AppState> setErrorOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setError(null);
      return appState;
    }
  };
  static Func1<AppState, AppState> setSettingsOnMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setShowSettings(true);
      return appState;
    }
  };
  static Func1<AppState, AppState> setSettingsOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setShowSettings(false);
      return appState;
    }
  };
  static Func1<AppState, AppState> setLoadingOnMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setLoading(true);
      return appState;
    }
  };
  static Func1<AppState, AppState> setLoadingOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setLoading(false);
      return appState;
    }
  };
  static Func1<AppState, AppState> setPopupErrorOnMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setPopupError(parseException(appState.getException()));
      return appState;
    }
  };
  static Func1<AppState, AppState> setPopupErrorOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setPopupError(null);
      return appState;
    }
  };
  static Func1<AppState, AppState> setErrorInSettings = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setErrorInSettings(parseException(appState.getException()));
      return appState;
    }
  };
  static Func1<AppState, AppState> setErrorOffInSettings = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setErrorInSettings(null);
      return appState;
    }
  };
  static Func1<AppState, AppState> setExceptionOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setException(null);
      return appState;
    }
  };

  private static String parseException(Throwable thr) {
    if(thr instanceof HttpException) {
      HttpException he = (HttpException) thr;
      int code = he.code();
      switch (code) {
        case 403:
          return "Github no likee.";
        case 404:
          return "Github canny find 'im.";
        default:
          return "Github with a new exciting error.";
      }
    }
    return "Some kind of error, innit";
  }
}
