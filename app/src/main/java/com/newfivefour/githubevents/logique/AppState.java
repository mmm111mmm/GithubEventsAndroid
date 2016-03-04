package com.newfivefour.githubevents.logique;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import rx.functions.Func1;

public class AppState extends BaseObservable {

  public static AppState appState = new AppState();

  private ArrayList events = new ArrayList<Event>() {{
  }};
  private String title = "newfivefour";
  private String userUrl = "";
  private String avatarUrl = "";
  private boolean loading = true;
  private boolean error = false;
  private boolean popupError = false;
  private boolean showSettings = false;
  private Throwable exception;
  private JsonObject userJson;
  private JsonArray eventsJson;

  @Bindable
  public ArrayList<Event> getEvents() {
    return events;
  }

  void setEvents(ArrayList events) {
    this.events = events;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.events);
  }

  @Bindable
  public String getTitle() {
    return title;
  }

  void setTitle(String title) {
    this.title = title;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.title);
  }

  @Bindable
  public String getAvatarUrl() {
    return avatarUrl;
  }

  void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.avatarUrl);
  }

  @Bindable
  public String getUserUrl() {
    return userUrl;
  }

  void setUserUrl(String userUrl) {
    this.userUrl = userUrl;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.userUrl);
  }

  @Bindable
  public boolean isLoading() {
    return loading;
  }

  void setLoading(boolean loading) {
    this.loading = loading;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.loading);
  }
  Func1<AppState, AppState> setLoadingOnMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setLoading(true);
        return appState;
      }
    };
  }
  Func1<AppState, AppState> setLoadingOffMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setLoading(false);
        return appState;
      }
    };
  }


  @Bindable
  public boolean isShowSettings() {
    return showSettings;
  }

  void setShowSettings(boolean showSettings) {
    this.showSettings = showSettings;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.showSettings);
  }
  Func1<AppState, AppState> setSettingsOnMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setShowSettings(true);
        return appState;
      }
    };
  }
  Func1<AppState, AppState> setSettingsOffMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setShowSettings(false);
        return appState;
      }
    };
  }

  @Bindable
  public boolean isError() {
    return error;
  }

  void setError(boolean error) {
    this.error = error;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.error);
  }

  Func1<AppState, AppState> setErrorOnMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setError(true);
        return appState;
      }
    };
  }
  Func1<AppState, AppState> setErrorOffMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setError(false);
        return appState;
      }
    };
  }

  public void setException(Throwable exception) {
    this.exception = exception;
  }

  void setUserJson(JsonObject userJson) {
    this.userJson = userJson;
  }

  void setEventsJson(JsonArray eventsJson) {
    this.eventsJson = eventsJson;
  }

  public Throwable getException() {
    return exception;
  }

  public JsonArray getEventsJson() {
    return eventsJson;
  }

  public JsonObject getUserJson() {
    return userJson;
  }

  @Bindable
  public boolean isPopupError() {
    return popupError;
  }

  public void setPopupError(boolean popupError) {
    this.popupError = popupError;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.popupError);
  }

  Func1<AppState, AppState> setPopupErrorOnMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setPopupError(true);
        return appState;
      }
    };
  }
  Func1<AppState, AppState> setPopupErrorOffMap() {
    return new Func1<AppState, AppState>() {
      @Override
      public AppState call(AppState appState) {
        appState.setPopupError(false);
        return appState;
      }
    };
  }

  public static class Event extends BaseObservable {
    private String type;
    private String time;
    private String repoUrl;
    private String repoName;

    public Event() {}

    public Event(String type, String time, String repo_url, String repo_name) {
      this.type = type;
      this.time = time;
      this.repoUrl = repo_url;
      this.repoName = repo_name;
    }

    @Bindable
    public String getType() {
      return type;
    }

    void setType(String type) {
      this.type = type;
    }

    @Bindable
    public String getTime() {
      return time;
    }

    void setTime(String time) {
      this.time = time;
    }

    @Bindable
    public String getRepoName() {
      return repoName;
    }

    void setRepoName(String repoName) {
      this.repoName = repoName;
    }

    @Bindable
    public String getRepoUrl() {
      return repoUrl;
    }

    void setRepoUrl(String repoUrl) {
      this.repoUrl = repoUrl;
    }
  }
}
