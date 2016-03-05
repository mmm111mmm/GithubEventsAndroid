package com.newfivefour.githubevents.logique;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

public class UseCases {

  static Action1<AppState> emptySubscribe = new Action1<AppState>() {
    @Override public void call(AppState appState) {}
  };
   // Filters
  static Func1<AppState, Boolean> areEvents = new Func1<AppState, Boolean>() {
     @Override
     public Boolean call(AppState appState) {
      return AppState.appState.getEvents() != null && AppState.appState.getEvents().size() > 0;
    }
   };
  static Func1<AppState, Boolean> areNoEvents = new Func1<AppState, Boolean>() {
    @Override
    public Boolean call(AppState appState) {
      return AppState.appState.getEvents() == null || AppState.appState.getEvents().size() == 0;
    }
  };
  static Func1<Void, Boolean> hasNoShownSettings = new Func1<Void, Boolean>() {
    @Override public Boolean call(Void as) {
      return !AppState.appState.isShowSettings();
    }
  };
  static Func1<Void, Boolean> hasShownSettings = new Func1<Void, Boolean>() {
    @Override public Boolean call(Void as) {
      return AppState.appState.isShowSettings();
    }
  };
  static Func1<AppState, Boolean> hasException = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState as) {
      return as.getException() != null;
    }
  };
  static Func1<AppState, Boolean> noException = new Func1<AppState, Boolean>() {
    @Override public Boolean call(AppState as) {
      return as.getException() == null;
    }
  };

  public static void init() {
    // Server observable list
    // Refresh request
    Observable<AppState> updateRequest = Actions.ServerUpdateAction.react();
    // Observable to combine network requests
    ConnectableObservable<AppState> serverRefresh = updateRequest
    .flatMap(Server.zippedEventsUsers()).publish();
    // Observables that filter on exception
    Observable<AppState> serverRefreshParsed = serverRefresh.filter(noException);
    Observable<AppState> serverRefreshFail = serverRefresh.filter(hasException);

    // Subscriptions
    // Start loading and clear errors on service load
    updateRequest
    .map(AppState.appState.setExceptionOffMap())
    .map(AppState.appState.setLoadingOnMap())
    .map(AppState.appState.setPopupErrorOffMap())
    .map(AppState.appState.setErrorOffMap())
    .subscribe(emptySubscribe);
    // Stop loading page after a server refresh
    serverRefresh
    .map(AppState.appState.setLoadingOffMap())
    .subscribe(emptySubscribe);
    // Parse good server return
    serverRefreshParsed
    .subscribe(emptySubscribe);
    // Show page error when no content
    serverRefreshFail.filter(areNoEvents)
    .map(AppState.appState.setErrorOnMap())
    .subscribe(emptySubscribe);
    // Show popup error when content
    serverRefreshFail.filter(areEvents)
    .map(AppState.appState.setPopupErrorOnMap())
    .subscribe(emptySubscribe);
    // Show settings
    Actions.SettingsAction.react()
    .map(AppState.appState.setSettingsOnMap())
    .subscribe(emptySubscribe);
    // Dismiss settings
    Actions.SettingsAction.reactNoSettings()
    .map(AppState.appState.setSettingsOffMap())
    .subscribe(emptySubscribe);
    // Dismiss settings box on good parse
    serverRefreshParsed
    .map(AppState.appState.setSettingsOffMap())
    .subscribe(emptySubscribe);

    serverRefresh.connect();
  }

}
