package com.newfivefour.githubevents.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

  public static String getTimeAgoFromDateString(String s) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date now = new Date();
    try {
      Date date = format.parse(s);
      int seconds = (int) Math.floor((now.getTime() - date.getTime()) / 1000);
      if(seconds < 0) {
        Log.d("HIYA", "Recent is       " + s);
        Log.d("HIYA", "Current is time " + format.format(now));
        return "Very, very recently";
      }
      int interval = (int) Math.floor(seconds / 31536000);
      if(interval == 1) {
        return interval + " year ago";
      } else if (interval > 1) {
        return interval + " years ago";
      }
      interval = (int) Math.floor(seconds / 2592000);
      if(interval == 1) {
        return interval + " month ago";
      } else if (interval > 1) {
        return interval + " months ago";
      }
      interval = (int) Math.floor(seconds / 86400);
      if(interval == 1) {
        return interval + " day ago";
      } else if (interval > 1) {
        return interval + " days ago";
      }
      interval = (int) Math.floor(seconds / 3600);
      if(interval == 1) {
        return interval + " hour ago";
      } else if (interval > 1) {
        return interval + " hours ago";
      }
      interval = (int) Math.floor(seconds / 60);
      if(interval == 1) {
        return interval + " minute ago";
      } else if(interval > 1) {
        return interval + " minutes ago";
      }
      interval = (int) Math.floor(seconds);
      if(interval == 1) {
        return interval + " second ago";
      } else if(interval > 1) {
        return interval + " seconds ago";
      }
    } catch (Exception e) {
      Log.d("HIYA", "Couldn't parse date: " + e.getMessage());
      e.printStackTrace();
    }
    return "unknown time ago";
  }

}
