package com.newfivefour.githubevents;

import java.util.ArrayList;

public class AppState {

  private ArrayList events = new ArrayList<Event>() {{
    add(new Event("PushEvent", "2016-02-24T03:38:05Z", "https://github.com/newfivefour/nff-github-events", "nff-github-events"));
    add(new Event("PushEvent", "2016-02-23T01:38:05Z", "https://github.com/newfivefour/nff-github-events", "nff-github-events"));
    add(new Event("PushEvent", "2016-01-23T02:38:05Z", "https://github.com/newfivefour/nff-github-events", "nff-github-events"));
  }};
  private String title = "newfivefour's events";
  private String userUrl = "https://github.com/newfivefour";
  private String avatarUrl = "https://avatars0.githubusercontent.com/u/7628223?v=3&s=460";

  public ArrayList<Event> getEvents() {
    return events;
  }

  public void setEvents(ArrayList events) {
    this.events = events;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getUserUrl() {
    return userUrl;
  }

  public void setUserUrl(String userUrl) {
    this.userUrl = userUrl;
  }

  public static class Event {
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

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public String getRepoName() {
      return repoName;
    }

    public void setRepoName(String repoName) {
      this.repoName = repoName;
    }

    public String getRepoUrl() {
      return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
      this.repoUrl = repoUrl;
    }
  }
}
