package com.newfivefour.githubevents.logique;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;

public class AppState extends BaseObservable {

  public static AppState appState = new AppState();

  private ArrayList events = new ArrayList<Event>() {{}};
  private String title = "newfivefour";
  private String attemptedUsername = "";
  private String settingsUsername = "";
  private String userUrl = "";
  private String avatarUrl = "";
  private boolean loading = true;
  private String error;
  private String errorInSettings;
  private String popupError;
  private boolean showSettings = false;
  private Throwable exception;

  @Bindable public ArrayList<Event> getEvents() {
    return events;
  }

  void setEvents(ArrayList events) {
    this.events = events;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.events);
  }

  @Bindable public String getTitle() {
    return title;
  }

  void setTitle(String title) {
    this.title = title;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.title);
  }

  @Bindable public String getAvatarUrl() {
    return avatarUrl;
  }

  void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.avatarUrl);
  }

  @Bindable public String getUserUrl() {
    return userUrl;
  }

  void setUserUrl(String userUrl) {
    this.userUrl = userUrl;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.userUrl);
  }

  @Bindable public boolean isLoading() {
    return loading;
  }

  void setLoading(boolean loading) {
    this.loading = loading;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.loading);
  }

  @Bindable public boolean isShowSettings() {
    return showSettings;
  }

  void setShowSettings(boolean showSettings) {
    this.showSettings = showSettings;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.showSettings);
  }

  @Bindable public String getError() {
    return error;
  }

  void setError(String error) {
    this.error = error;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.error);
  }

  public void setException(Throwable exception) {
    this.exception = exception;
  }

  public Throwable getException() {
    return exception;
  }

  @Bindable public String getPopupError() {
    return popupError;
  }

  public void setPopupError(String popupError) {
    this.popupError = popupError;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.popupError);
  }

  @Bindable public String getAttemptedUsername() {
    return attemptedUsername;
  }

  public void setAttemptedUsername(String attemptedUsername) {
    this.attemptedUsername = attemptedUsername;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.attemptedUsername);
  }

  @Bindable public String getErrorInSettings() {
    return errorInSettings;
  }

  public void setErrorInSettings(String errorInSettings) {
    this.errorInSettings = errorInSettings;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.errorInSettings);
  }

  @Bindable public String getSettingsUsername() {
    return settingsUsername;
  }

  public void setSettingsUsername(String settingsUsername) {
    this.settingsUsername = settingsUsername;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.settingsUsername);
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

    @Bindable public String getType() {
      return type;
    }

    void setType(String type) {
      this.type = type;
    }

    @Bindable public String getTime() {
      return time;
    }

    void setTime(String time) {
      this.time = time;
    }

    @Bindable public String getRepoName() {
      return repoName;
    }

    void setRepoName(String repoName) {
      this.repoName = repoName;
    }

    @Bindable public String getRepoUrl() {
      return repoUrl;
    }

    void setRepoUrl(String repoUrl) {
      this.repoUrl = repoUrl;
    }
  }
}
