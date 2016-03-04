package com.newfivefour.githubevents.logique;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.observables.ConnectableObservable;

public class UseCases {

  static Action1<AppState> emptySubscribe = new Action1<AppState>() {
    @Override public void call(AppState appState) {}
  };
  static Func1<AppState, AppState> parseUserAndEventsJson = new Func1<AppState, AppState>() { // Parse JSON
    @Override
    public AppState call(AppState appState) {
      JsonArray o = appState.getEventsJson();
      JsonObject user = appState.getUserJson();
      appState.setAvatarUrl(user.get("avatar_url").getAsString());
      appState.setTitle(user.get("login").getAsString());
      appState.setUserUrl(user.get("html_url").getAsString());
      ArrayList<AppState.Event> events = new ArrayList<>();
      for (JsonElement a : o) {
        AppState.Event e = new AppState.Event();
        e.setType(a.getAsJsonObject().get("type").getAsString());
        JsonObject repo = a.getAsJsonObject().get("repo").getAsJsonObject();
        e.setRepoName(repo.get("name").getAsString());
        e.setRepoUrl("https://github.com/" + repo.get("name").getAsString());
        e.setTime(a.getAsJsonObject().get("created_at").getAsString());
        events.add(e);
      }
      appState.setEvents(events);
      return appState;
    }
  };
   // Filters
  static Func1<AppState, Boolean> areEvents = new Func1<AppState, Boolean>() {
     @Override
     public Boolean call(AppState appState) {
      return AppState.appState.getEvents() == null || AppState.appState.getEvents().size() == 0;
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
    final Observable<AppState> appStateObservable = Observable.just(AppState.appState);
    Func1<Object, Observable<AppState>> appStateMap = new Func1<Object, Observable<AppState>>() {
      @Override public Observable<AppState> call(Object s) {
        return appStateObservable;
      }
    };
    Func1<String, Observable<AppState>> serverEventsMap = new Func1<String, Observable<AppState>>() {
      @Override public Observable<AppState> call(String username) {
      return Observable.zip(
        appStateObservable,
        Server.ghUser.getUser(username).compose(Server.composeAndroidSchedulers(new JsonObject())),
        Server.ghEvents.listEvents(username).compose(Server.composeAndroidSchedulers(new JsonArray())),
        new Func3<AppState, JsonObject, JsonArray, AppState>() {
          @Override public AppState call(AppState appState, JsonObject jsonObject, JsonArray jsonElements) {
            appState.setException(null);
            appState.setEventsJson(jsonElements);
            appState.setUserJson(jsonObject);
            return AppState.appState;
          }
        }).onErrorReturn(new Func1<Throwable, AppState>() {
          @Override public AppState call(Throwable throwable) {
          AppState.appState.setException(throwable);
          return AppState.appState;
        }});
      }};

    // Refresh server with new name
    Observable<String> serverUpdateRequest = Actions.ServerUpdateAction.react();
    ConnectableObservable<AppState> serverRefresh = serverUpdateRequest.flatMap(serverEventsMap).publish();
    Observable<AppState> serverRefreshParsed = serverRefresh.filter(noException).map(parseUserAndEventsJson);
    Observable<AppState> serverRefreshFail = serverRefresh.filter(hasException);

    // Start loading and clear errors on service load
    serverUpdateRequest.flatMap(appStateMap)
    .map(AppState.appState.setLoadingOnMap())
    .map(AppState.appState.setPopupErrorOffMap())
    .map(AppState.appState.setErrorOffMap())
    .subscribe(emptySubscribe);
    // Stop loading page after a server refresh
    serverRefresh.map(AppState.appState.setLoadingOffMap())
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
    .flatMap(appStateMap)
    .map(AppState.appState.setSettingsOnMap())
    .subscribe(emptySubscribe);
    // Dismiss settings
    Actions.SettingsAction.reactNoSettings()
    .flatMap(appStateMap)
    .map(AppState.appState.setSettingsOffMap())
    .subscribe(emptySubscribe);
    // Dismiss settings box on good parse
    serverRefreshParsed
    .map(AppState.appState.setSettingsOffMap())
    .subscribe(emptySubscribe);

    serverRefresh.connect();
  }

}
