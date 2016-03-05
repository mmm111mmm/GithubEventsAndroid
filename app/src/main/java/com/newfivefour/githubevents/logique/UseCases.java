package com.newfivefour.githubevents.logique;

import rx.Observable;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

public class UseCases {

  static Action1<AppState> emptySubscribe = new Action1<AppState>() {
    @Override public void call(AppState appState) {}
  };

  public static void init() {
    // Server observable list
    // Refresh request
    Observable<AppState> updateRequest = Actions.ServerUpdateAction.react();
    // Observable to combine network requests
    ConnectableObservable<AppState> serverRefresh = updateRequest
    .flatMap(Server.zippedEventsUsers()).publish();
    // Observables that filter on exception
    Observable<AppState> serverRefreshParsed = serverRefresh.filter(AppStateFilters.noException);
    Observable<AppState> serverRefreshFail = serverRefresh.filter(AppStateFilters.hasException);

    // Subscriptions
    // Start loading and clear errors on service load
    updateRequest
    .map(AppStateMaps.setExceptionOffMap)
    .map(AppStateMaps.setLoadingOnMap)
    .map(AppStateMaps.setPopupErrorOffMap)
    .map(AppStateMaps.setErrorOffMap)
    .subscribe(emptySubscribe);
    // Stop loading page after a server refresh
    serverRefresh
    .map(AppStateMaps.setLoadingOffMap)
    .subscribe(emptySubscribe);
    // Parse good server return
    serverRefreshParsed
    .subscribe(emptySubscribe);
    // Show page error when no content
    serverRefreshFail.filter(AppStateFilters.areNoEvents)
    .map(AppStateMaps.setErrorOnMap)
    .subscribe(emptySubscribe);
    // Show popup error when content
    serverRefreshFail.filter(AppStateFilters.areEvents)
    .map(AppStateMaps.setPopupErrorOnMap)
    .subscribe(emptySubscribe);
    // Show settings
    Actions.SettingsAction.react()
    .map(AppStateMaps.setSettingsOnMap)
    .subscribe(emptySubscribe);
    // Dismiss settings
    Actions.SettingsAction.reactNoSettings()
    .map(AppStateMaps.setSettingsOffMap)
    .subscribe(emptySubscribe);
    // Dismiss settings box on good parse
    serverRefreshParsed
    .map(AppStateMaps.setSettingsOffMap)
    .subscribe(emptySubscribe);

    serverRefresh.connect();
  }

}
