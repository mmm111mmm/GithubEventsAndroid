package com.newfivefour.githubevents.logique;

import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class Actions {

  public interface ActionsCallback {
    void sendAction(String message);
  }

  public static Func1<String, Boolean> isRefresh = new Func1<String, Boolean>() {
    @Override public Boolean call(String action) {
      return action.equals("REFRESH");
    }
  };

  public static Func1<String, Boolean> isSettings = new Func1<String, Boolean>() {
    @Override public Boolean call(String action) {
      return action.equals("SETTINGS");
    }
  };

  public static Func1<String, Boolean> isNoSettings = new Func1<String, Boolean>() {
    @Override public Boolean call(String action) {
      return action.equals("NOSETTINGS");
    }
  };

  private static ActionsCallback callback;
  public static Observable<String> actionSent = Observable.create(new ActionsObservable());

  public static void send(String message) {
    callback.sendAction(message);
  }

  public static class ActionsObservable implements  Observable.OnSubscribe<String> {
    ArrayList<Subscriber<? super String>> subscribers = new ArrayList<>();

    public ActionsObservable() {
      Actions.callback = new ActionsCallback() {
        @Override
        public void sendAction(String message) {
          Log.d("HIYA", "Action: " + message);
          for (Subscriber<? super String> sub : subscribers) {
            sub.onNext(message);
          }
        }
      };
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
      subscribers.add(subscriber);
    }
  }
}
