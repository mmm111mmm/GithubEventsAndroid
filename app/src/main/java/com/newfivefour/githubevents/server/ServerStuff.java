package com.newfivefour.githubevents.server;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerStuff {

  public interface GitHubService {
    @GET("user/{user}")
    Observable<User> listRepos(@Path("user") String user);
  }

  public void stuff() {
    new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(GitHubService.class)
            .listRepos("octocat")
            .subscribeOn(Schedulers.newThread()) // Create a new Thread
            .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
            .subscribe(new Subscriber<User>() {
              @Override public void onCompleted() { }

              @Override
              public void onError(Throwable e) {
                Log.d("HIYA", "An error!: " + e.getMessage());
              }

              @Override
              public void onNext(User user) {
                Log.d("HIYA", "So we've now got some text: " + user.avatar_url);
              }
            });
  }

  private static class User {
    public String avatar_url;
  }
}
