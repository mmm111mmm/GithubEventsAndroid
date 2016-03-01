package com.newfivefour.githubevents.logique;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class UseCases {

  interface GitHubUser {
    @GET("users/{user}")
    Observable<JsonObject> getUser(@Path("user") String user);
  }
  interface GitHubEvents {
    @GET("users/{user}/events")
    Observable<JsonArray> listEvents(@Path("user") String user);
  }

  static private Retrofit sRepo = new Retrofit.Builder()
          .baseUrl("https://api.github.com")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
  static private GitHubUser ghUser = sRepo.create(GitHubUser.class);
  static private GitHubEvents ghEvents = sRepo.create(GitHubEvents.class);

  // State modifiers
  static Func1<AppState, AppState> loadingOn = new Func1<AppState, AppState>() { // loading on
    @Override
    public AppState call(AppState as) {
      as.setLoading(true);
      return as;
    }
  };
  static Func1<AppState, AppState> loadingOff = new Func1<AppState, AppState>() { // loading off
    @Override
    public AppState call(AppState as) {
      as.setLoading(false);
      return as;
    }
  };
  static Func1<AppState, AppState> errorOn = new Func1<AppState, AppState>() { // Set error
    @Override
    public AppState call(AppState as) {
      as.setError(true);
      return as;
    }
  };
  static Func1<AppState, AppState> errorOff = new Func1<AppState, AppState>() { // Set error
    @Override
    public AppState call(AppState as) {
      as.setError(false);
      return as;
    }
  };
  static Func1<AppState, AppState> settingsOn = new Func1<AppState, AppState>() { // Settings on
    @Override
    public AppState call(AppState as) {
      as.setShowSettings(true);
      return as;
    }
  };
  static Func1<AppState, AppState> settingsOff = new Func1<AppState, AppState>() { // Settings on
    @Override
    public AppState call(AppState as) {
      as.setShowSettings(false);
      return as;
    }
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
  // Misc...?
  public static Func1<Object, String> usernameFromAppState = new Func1<Object, String>() {
    @Override
    public String call(Object o) {
      return AppState.appState.getTitle();
    }
  };

  public static void init() {
    // Server observable list
    final Observable<AppState> appStateObservable = Observable.just(AppState.appState);
    Func1<String, Observable<AppState>> appStateMap = new Func1<String, Observable<AppState>>() {
      @Override public Observable<AppState> call(String s) {
        return appStateObservable;
      }
    };
    Func1<String, Observable<AppState>> serverEventsMap = new Func1<String, Observable<AppState>>() {
      @Override public Observable<AppState> call(String username) {
      return Observable.zip(
        appStateObservable
          .map(errorOff)
          .map(loadingOn),
        ghUser.getUser(username)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread()),
        ghEvents.listEvents(username)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread()),
        new Func3<AppState, JsonObject, JsonArray, AppState>() {
          @Override public AppState call(AppState appState, JsonObject jsonObject, JsonArray jsonElements) {
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
    // Refresh observers: happy path
    Actions.actionSent
    .filter(Actions.isRefresh)
    .map(usernameFromAppState)
    .flatMap(serverEventsMap)
    .filter(hasException)
    .map(errorOn)
    .map(loadingOff)
    .subscribe(new Action1<AppState>() { // Go
       @Override public void call(AppState as) {
        Log.d("HIYA", "" + as.getException());
       }
    });
    // Refresh observers: sad path
    Actions.actionSent
    .filter(Actions.isRefresh)
    .map(usernameFromAppState)
    .flatMap(serverEventsMap)
    .filter(noException)
    .map(parseUserAndEventsJson)
    .map(loadingOff)
    .subscribe(new Action1<AppState>() {
      @Override public void call(AppState appState) {
        Log.d("HIYA", "NO ERROR");
      }
    });
    // Show settings
    Actions.actionSent
    .filter(Actions.isSettings)
    .flatMap(appStateMap)
    .map(settingsOn)
    .subscribe(new Action1<AppState>() {
      @Override public void call(AppState appState) { }
    });
    // Dismiss settings
    Actions.actionSent
    .filter(Actions.isNoSettings)
    .flatMap(appStateMap)
    .map(settingsOff)
    .subscribe(new Action1<AppState>() {
      @Override public void call(AppState appState) { }
    });
  }
}
