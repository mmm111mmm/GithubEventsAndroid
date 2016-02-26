package com.newfivefour.githubevents.server;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.newfivefour.githubevents.AppState;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class ServerStuff {

  public interface GitHubUser {
    @GET("users/{user}")
    Observable<JsonObject> getUser(@Path("user") String user);
  }

  public interface GitHubEvents {
    @GET("users/{user}/events")
    Observable<JsonArray> listEvents(@Path("user") String user);
  }

  public class UserAndEvents {
    public UserAndEvents(JsonObject user, JsonArray events) {
      this.events = events;
      this.user = user;
    }

    public JsonArray events;
    public JsonObject user;
  }

  public void stuff() {
    String login = "newfivefour";

    Retrofit repo = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    Observable<JsonObject> userObservable = repo
            .create(GitHubUser.class)
            .getUser(login)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());

    Observable<JsonArray> eventsObservable = repo
            .create(GitHubEvents.class)
            .listEvents(login)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());

    Observable<UserAndEvents> combined = Observable.zip(userObservable, eventsObservable, new Func2<JsonObject, JsonArray, UserAndEvents>() {
      @Override
      public UserAndEvents call(JsonObject jsonObject, JsonArray jsonElements) {
        return new UserAndEvents(jsonObject, jsonElements);
      }
    });

    combined
            .subscribe(new Subscriber<UserAndEvents>() {
              @Override
              public void onCompleted() {
              }

              @Override
              public void onError(Throwable e) {
                Log.d("HIYA", "An error!: " + e.getMessage());
              }

              @Override
              public void onNext(UserAndEvents o) {
                AppState.appState.setAvatarUrl(o.user.get("avatar_url").getAsString());
                AppState.appState.setTitle(o.user.get("login").getAsString());
                AppState.appState.setUserUrl(o.user.get("html_url").getAsString());
                ArrayList<AppState.Event> events = new ArrayList<>();
                for (JsonElement a : o.events) {
                  AppState.Event e = new AppState.Event();
                  e.setType(a.getAsJsonObject().get("type").getAsString());
                  JsonObject repo = a.getAsJsonObject().get("repo").getAsJsonObject();
                  e.setRepoName(repo.get("name").getAsString());
                  e.setRepoUrl(repo.get("url").getAsString());
                  e.setTime(a.getAsJsonObject().get("created_at").getAsString());
                  events.add(e);
                }
                AppState.appState.setEvents(events);
                AppState.appState.setLoading(false);
              }
            });
  }
}
