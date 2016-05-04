package com.newfivefour.githubevents.logique;

import rx.Observable;
import rx.functions.Func1;

public class ActionsFiltersMaps {
  // Filters
  static Func1<Actions.Action, Boolean> settingsOn = new Func1<Actions.Action, Boolean>() {
    @Override public Boolean call(Actions.Action action) {
      return action instanceof Actions.SettingsAction && ((Actions.SettingsAction) action).object;
    }
  };
  static Func1<Actions.Action, Boolean> settingsOff = new Func1<Actions.Action, Boolean>() {
    @Override public Boolean call(Actions.Action action) {
      return action instanceof Actions.SettingsAction && !((Actions.SettingsAction) action).object;
    }
  };

  static Func1<Actions.Action, Boolean> serverUpdate = new Func1<Actions.Action, Boolean>() {
    @Override public Boolean call(Actions.Action action) {
      return action instanceof Actions.ServerUpdateAction;
    }
  };
  static Func1<Actions.Action, Boolean> serverUpdateIfNoContent = new Func1<Actions.Action, Boolean>() {
    @Override public Boolean call(Actions.Action action) {
      return action instanceof Actions.ServerUpdateActionIfNothingThere;
    }
  };
  static Func1<Actions.Action, Boolean> serverUpdateUsername = new Func1<Actions.Action, Boolean>() {
    @Override public Boolean call(Actions.Action action) {
      return action instanceof Actions.ServerUpdateFromSettingsAction;
    }
  };
  // Maps
  static Func1<Actions.Action, Observable<AppState>> justState = new Func1<Actions.Action, Observable<AppState>>() {
    @Override public Observable<AppState> call(Actions.Action action) {
      return Observable.just(AppState.appState);
    }
  };
  static Func1<Actions.Action, Observable<AppState>> extractUsername = new Func1<Actions.Action, Observable<AppState>>() {
    @Override public Observable<AppState> call(Actions.Action action) {
      AppState.appState.setAttemptedUsername((String)action.object);
      return Observable.just(AppState.appState);
    }
  };
  static Func1<Actions.Action, Observable<AppState>> extractUsernameInSettings = new Func1<Actions.Action, Observable<AppState>>() {
    @Override public Observable<AppState> call(Actions.Action action) {
      AppState.appState.setAttemptedUsername((String)action.object);
      AppState.appState.setSettingsUsername((String) action.object);
      return Observable.just(AppState.appState);
    }
  };


}
