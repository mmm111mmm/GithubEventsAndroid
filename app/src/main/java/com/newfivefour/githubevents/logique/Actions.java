package com.newfivefour.githubevents.logique;

import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

public class Actions {


  static class SettingsAction extends Action<Boolean> {
    public SettingsAction(boolean show) {
      super(show);
    }
  }

  static class ServerUpdateAction extends Action<String> {
    public ServerUpdateAction(String username) {
      super(username);
    }
  }

  static class ServerUpdateActionIfNothingThere extends Action<String> {
    public ServerUpdateActionIfNothingThere(String username) {
      super(username);
    }
  }

  static class ServerUpdateFromSettingsAction extends Action<String> {
    public ServerUpdateFromSettingsAction(String username) {
      super(username);
    }
  }

  private static ActionsCallback callback;
  public static Observable<Action> actionSent = Observable.create(new ActionsObservable());

  public static void refresh() {
    callback.sendAction(new ServerUpdateAction(AppState.appState.getTitle()));
  }

  public static void refreshIfNothingThere() {
    callback.sendAction(new ServerUpdateActionIfNothingThere(AppState.appState.getTitle()));
  }

  public static void changeUsername(String username) {
    callback.sendAction(new ServerUpdateFromSettingsAction(username));
  }

  public static void settings(boolean show) {
    callback.sendAction(new SettingsAction(show));
  }

  public static class ActionsObservable implements  Observable.OnSubscribe<Action> {
    ArrayList<Subscriber<? super Action>> subscribers = new ArrayList<>();

    public ActionsObservable() {
      Actions.callback = new ActionsCallback() {
        @Override
        public void sendAction(Action message) {
          Log.d("HIYA", "Action: " + message);
          for (Subscriber<? super Action> sub : subscribers) {
            sub.onNext(message);
          }
        }
      };
    }

    @Override
    public void call(Subscriber<? super Action> subscriber) {
      subscribers.add(subscriber);
    }
  }

  public abstract static class Action<T> {
    public final T object;
    public Action(T o) {
      this.object = o;
    }
  }

  public interface ActionsCallback {
    void sendAction(Action message);
  }
}
