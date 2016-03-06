package com.newfivefour.githubevents.logique;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.newfivefour.githubevents.App;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class Server {

  interface GitHubUser {
    @GET("users/{user}")
    Observable<JsonObject> getUser(@Path("user") String user);
  }

  interface GitHubEvents {
    @GET("users/{user}/events")
    Observable<JsonArray> listEvents(@Path("user") String user);
  }

  static private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
  static {
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
  }
  static private OkHttpClient client = new OkHttpClient
          .Builder()
          .addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
              Request request = chain.request();
              if (App.isNetworkAvailable()) {
                int maxAge = 60; // read from cache for 1 minute
                request.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).build();
              } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
              }
              Response originalResponse = chain.proceed(request);
              return originalResponse.newBuilder()
                                     .header("Cache-Control", "max-age=600")
                                     .build();
            }
          })
          .cache(new Cache(App.sApp.getCacheDir(), 10 * 1024 * 1024))
          .addInterceptor(interceptor)
          .build();
  static private Retrofit sRepo = new Retrofit.Builder()
          .baseUrl("https://api.github.com")
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
  static public GitHubUser ghUser = sRepo.create(GitHubUser.class);
  static public GitHubEvents ghEvents = sRepo.create(GitHubEvents.class);

  static private Func1<AppState, Observable<AppState>> user() {
    return new Func1<AppState, Observable<AppState>>() {
      @Override
      public Observable<AppState> call(final AppState appState) {
        return ghUser.getUser(appState.getAttemptedUsername()).compose(schedulers(JsonObject.class)).map(new Func1<JsonObject, AppState>() {
          @Override
          public AppState call(JsonObject user) {
            appState.setAvatarUrl(user.get("avatar_url").getAsString());
            appState.setTitle(user.get("login").getAsString());
            appState.setUserUrl(user.get("html_url").getAsString());
            return appState;
          }
        });
      }
    };
  }

  static private Func1<AppState, Observable<AppState>> events() {
    return new Func1<AppState, Observable<AppState>>() {
      @Override
      public Observable<AppState> call(final AppState appState) {
        return ghEvents.listEvents(appState.getAttemptedUsername()).compose(Server.schedulers(JsonArray.class)).map(new Func1<JsonArray, AppState>() {
          @Override
          public AppState call(JsonArray jsonEvents) {
            ArrayList<AppState.Event> events = new ArrayList<>();
            for (JsonElement a : jsonEvents) {
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
        });
      }
    };
  }

  static public Func1<AppState, Observable<AppState>> zippedEventsUsers() {
    return new Func1<AppState, Observable<AppState>>() {
      @Override public Observable<AppState> call(final AppState appState) {
        return Observable.zip(
          Observable.just(appState).flatMap(user()),
          Observable.just(appState).flatMap(events()),
          new Func2<AppState, AppState, AppState>() {
            @Override public AppState call(AppState appState, AppState appState2) {
              return appState;
            }
          })
          .onErrorReturn(new Func1<Throwable, AppState>() {
            @Override
            public AppState call(Throwable throwable) {
              appState.setException(throwable);
              return appState;
            }});
      }
    };
  }

  private static <T> Observable.Transformer<T, T> schedulers(Class<T> hmm) {
    return new Observable.Transformer<T, T>() {
      @Override
      public Observable<T> call(Observable<T> jsonArrayObservable) {
        return jsonArrayObservable.subscribeOn(Schedulers.newThread())
                                  .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

}
