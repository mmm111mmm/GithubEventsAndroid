package com.newfivefour.githubevents.logique;

import rx.functions.Func1;

public class AppStateMaps {
  static Func1<AppState, AppState> setErrorOnMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setError(true);
      return appState;
    }
  };
  static Func1<AppState, AppState> setErrorOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setError(false);
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
      appState.setPopupError(true);
      return appState;
    }
  };
  static Func1<AppState, AppState> setPopupErrorOffMap = new Func1<AppState, AppState>() {
    @Override public AppState call(AppState appState) {
      appState.setPopupError(false);
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
