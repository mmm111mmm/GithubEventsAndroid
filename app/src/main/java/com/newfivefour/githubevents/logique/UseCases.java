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
    Observable<AppState> updateServerIfNothingThere = Actions.ServerUpdateActionIfNothingThere.react().filter(AppStateFilters.areNoEvents);
    Observable<AppState> updateRequest = Observable.merge(
            updateServerIfNothingThere,
            Actions.ServerUpdateAction.react(),
            Actions.ServerUpdateFromSettingsAction.react());
    ConnectableObservable<AppState> serverRefresh = updateRequest.flatMap(Server.zippedEventsUsers()).publish();
    Observable<AppState> serverRefreshParsed = serverRefresh.filter(AppStateFilters.noException);
    Observable<AppState> serverRefreshFail = serverRefresh.filter(AppStateFilters.hasException);
    Observable<AppState> serverFailNoContent = serverRefreshFail.filter(AppStateFilters.areNoEvents);
    Observable<AppState> serverFailContent = serverRefreshFail.filter(AppStateFilters.areEvents);
    Observable<AppState> serverFailInSettings = serverRefreshFail.filter(AppStateFilters.hasShownSettings);
    Observable<AppState> serverFailNoSettingsWContent = serverFailContent.filter(AppStateFilters.hasNoShownSettings);

    // Subscriptions
    // Start loading and clear errors on service load
    updateRequest
    .map(AppStateMaps.setExceptionOffMap)
    .map(AppStateMaps.setLoadingOnMap)
    .map(AppStateMaps.setErrorOffInSettings)
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
    serverFailNoContent
    .map(AppStateMaps.setErrorOnMap)
    .subscribe(emptySubscribe);
    // Popup error when content
    serverFailNoSettingsWContent
    .map(AppStateMaps.setPopupErrorOnMap)
    .subscribe(emptySubscribe);
    // Show settings
    Actions.SettingsAction.react()
    .map(AppStateMaps.setSettingsOnMap)
    .subscribe(emptySubscribe);
    // Dismiss settings
    Actions.SettingsAction.reactNoSettings()
    .map(AppStateMaps.setSettingsOffMap)
    .map(AppStateMaps.setErrorOffInSettings)
    .subscribe(emptySubscribe);
    // Dismiss settings box on good parse
    serverRefreshParsed
    .map(AppStateMaps.setSettingsOffMap)
    .subscribe(emptySubscribe);
    // Show error in editttext when refresh in settings
    serverFailInSettings
    .map(AppStateMaps.setErrorInSettings)
    .subscribe(emptySubscribe);

    // TODO: Time date seems to jump on update

    serverRefresh.connect();
  }

}
