package com.newfivefour.githubevents.logique;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
  static private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

  static {
    interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
  }

  static private Retrofit sRepo = new Retrofit.Builder()
          .baseUrl("https://api.github.com")
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
  static public GitHubUser ghUser = sRepo.create(GitHubUser.class);
  static public GitHubEvents ghEvents = sRepo.create(GitHubEvents.class);

  public static <T> Observable.Transformer<T,T> composeAndroidSchedulers(T hmm) {
    return new Observable.Transformer<T, T>() {
      @Override
      public Observable<T> call(Observable<T> jsonArrayObservable) {
        return jsonArrayObservable.subscribeOn(Schedulers.newThread())
                                  .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }
}
