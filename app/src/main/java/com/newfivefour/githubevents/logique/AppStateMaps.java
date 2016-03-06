package com.newfivefour.githubevents.logique;

import com.newfivefour.githubevents.utils.Utils;

import rx.functions.Func1;

public class AppStateMaps {
  static Func1<AppState, AppState> setErrorOnMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setError(Utils.parseNetworkException(appState.getException()));
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
      appState.setPopupError(Utils.parseNetworkException(appState.getException()));
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
      appState.setErrorInSettings(Utils.parseNetworkException(appState.getException()));
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

}
