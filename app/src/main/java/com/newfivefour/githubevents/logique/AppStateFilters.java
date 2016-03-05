package com.newfivefour.githubevents.logique;

import rx.functions.Func1;

public class AppStateFilters {
  static Func1<AppState, Boolean> areEvents = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState appState) {
      return appState.getEvents() != null && appState.getEvents().size() > 0;
    }
  };
  static Func1<AppState, Boolean> areNoEvents = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState appState) {
      return appState.getEvents() == null || appState.getEvents().size() == 0;
    }
  };
  static Func1<AppState, Boolean> hasNoShownSettings = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState appState) {
      return !appState.isShowSettings();
    }
  };
  static Func1<AppState, Boolean> hasShownSettings = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState appState) {
      return appState.isShowSettings();
    }
  };
  static Func1<AppState, Boolean> hasException = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState appState) {
      return appState.getException() != null;
    }
  };
  static Func1<AppState, Boolean> noException = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState as) {
      return as.getException() == null;
    }
  };
}
