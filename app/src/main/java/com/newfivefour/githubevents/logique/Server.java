package com.newfivefour.githubevents.logique;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class Server {

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
  static public GitHubUser ghUser = sRepo.create(GitHubUser.class);
  static public GitHubEvents ghEvents = sRepo.create(GitHubEvents.class);
}
