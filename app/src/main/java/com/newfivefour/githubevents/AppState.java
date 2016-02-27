package com.newfivefour.githubevents;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;

public class AppState extends BaseObservable {

  public static AppState appState = new AppState();

  private ArrayList events = new ArrayList<Event>() {{
    add(new Event("PushEvent", "2016-02-24T03:38:05Z", "https://github.com/newfivefour/nff-github-events", "nff-github-events"));
  }};
  private String title = "newfivefour's events";
  private String userUrl = "https://github.com/newfivefour";
  private String avatarUrl = "https://avatars0.githubusercontent.com/u/7628223?v=3&s=460";
  private boolean loading = true;
  private boolean error = false;
  private boolean showSettings = false;

  @Bindable
  public ArrayList<Event> getEvents() {
    return events;
  }

  public void setEvents(ArrayList events) {
    this.events = events;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.events);
  }

  @Bindable
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.title);
  }

  @Bindable
  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.avatarUrl);
  }

  @Bindable
  public String getUserUrl() {
    return userUrl;
  }

  public void setUserUrl(String userUrl) {
    this.userUrl = userUrl;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.userUrl);
  }

  @Bindable
  public boolean isLoading() {
    return loading;
  }

  public void setLoading(boolean loading) {
    this.loading = loading;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.loading);
  }

  @Bindable
  public boolean isShowSettings() {
    return showSettings;
  }

  public void setShowSettings(boolean showSettings) {
    this.showSettings = showSettings;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.showSettings);
  }

  @Bindable
  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
    notifyPropertyChanged(com.newfivefour.githubevents.BR.error);
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

    public void setType(String type) {
      this.type = type;
    }

    @Bindable
    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    @Bindable
    public String getRepoName() {
      return repoName;
    }

    public void setRepoName(String repoName) {
      this.repoName = repoName;
    }

    @Bindable
    public String getRepoUrl() {
      return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
      this.repoUrl = repoUrl;
    }
  }
}
