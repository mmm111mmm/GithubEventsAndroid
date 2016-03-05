package com.newfivefour.githubevents.logique;

import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class Actions {


  public static class SettingsAction extends Action<Boolean> {
    public SettingsAction(boolean show) {
      super(show);
    }
    public static Observable<AppState> reactNoSettings() {
      return actionSent.filter(new Func1<Action, Boolean>() {
        @Override
        public Boolean call(Action action) {
          return action instanceof SettingsAction && !((SettingsAction)action).object;
        }
      }).flatMap(new Func1<Action, Observable<AppState>>() {
        @Override
        public Observable<AppState> call(Action action) {
          return Observable.just(AppState.appState);
        }
      });
    }
    public static Observable<AppState> react() {
      return actionSent.filter(new Func1<Action, Boolean>() {
        @Override
        public Boolean call(Action action) {
          return action instanceof SettingsAction && ((SettingsAction)action).object;
        }
      }).flatMap(new Func1<Action, Observable<AppState>>() {
        @Override
        public Observable<AppState> call(Action action) {
          return Observable.just(AppState.appState);
        }
      });
    }
  }

  public static class ServerUpdateAction extends Action<String> {
    public ServerUpdateAction(String username) {
      super(username);
    }
    public static Observable<AppState> react() {
      return actionSent.filter(new Func1<Action, Boolean>() {
        @Override
        public Boolean call(Action action) {
          return action instanceof ServerUpdateAction;
        }
      })
      .flatMap(new Func1<Action, Observable<AppState>>() {
        @Override
        public Observable<AppState> call(Action action) {
          AppState.appState.setAttemptedUsername(((ServerUpdateAction)action).object);
          return Observable.just(AppState.appState);
        }
      });
    }
  }

  public static class ServerUpdateActionIfNothingThere extends Action<String> {
    public ServerUpdateActionIfNothingThere(String username) {
      super(username);
    }
    public static Observable<AppState> react() {
      return actionSent.filter(new Func1<Action, Boolean>() {
        @Override
        public Boolean call(Action action) {
          return action instanceof ServerUpdateActionIfNothingThere;
        }
      })
      .flatMap(new Func1<Action, Observable<AppState>>() {
      @Override public Observable<AppState> call(Action action) {
          AppState.appState.setAttemptedUsername(((ServerUpdateActionIfNothingThere)action).object);
          return Observable.just(AppState.appState);
       }
      });
    }
  }

  public static class ServerUpdateFromSettingsAction extends Action<String> {
    public ServerUpdateFromSettingsAction(String username) {
      super(username);
    }
    public static Observable<AppState> react() {
      return actionSent.filter(new Func1<Action, Boolean>() {
        @Override public Boolean call(Action action) {
          return action instanceof ServerUpdateFromSettingsAction;
        }
      })
      .flatMap(new Func1<Action, Observable<AppState>>() {
        @Override public Observable<AppState> call(Action action) {
          AppState.appState.setAttemptedUsername(((ServerUpdateFromSettingsAction)action).object);
          AppState.appState.setSettingsUsername(((ServerUpdateFromSettingsAction)action).object);
          return Observable.just(AppState.appState);
        }
      });
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
