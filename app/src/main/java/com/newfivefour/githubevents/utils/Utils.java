package com.newfivefour.githubevents.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.adapter.rxjava.HttpException;

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

  public static Activity scanForActivity(Context cont) {
    if (cont == null)
      return null;
    else if (cont instanceof Activity)
      return (Activity)cont;
    else if (cont instanceof ContextWrapper)
      return scanForActivity(((ContextWrapper)cont).getBaseContext());

    return null;
  }

  public static CoordinatorLayout scanForCoordinator(View cont) {
    if (cont == null) {
      Log.d("HIYA", "Couldn't find a Coordinator view. Bad sign. Bad sign.");
      return null;
    } else if (cont instanceof CoordinatorLayout)
      return (CoordinatorLayout)cont;
    else
      return scanForCoordinator((View)cont.getParent());
  }

  public static void dismissDialogByTag(Context context, String tag) {
    Activity c = Utils.scanForActivity(context);
    if(c!=null && c instanceof FragmentActivity) {
      Fragment f = ((FragmentActivity)c).getSupportFragmentManager().findFragmentByTag(tag);
      ((DialogFragment)f).dismiss();
    }
  }

  public static String parseNetworkException(Throwable thr) {
    if(thr instanceof HttpException) {
      HttpException he = (HttpException) thr;
      int code = he.code();
      switch (code) {
        case 403:
          return "Github no likee.";
        case 404:
          return "Github canny find 'im.";
        default:
          return "Github with a new exciting error.";
      }
    }
    if(thr instanceof UnknownHostException) {
      return "Internet connection no worky.";
    }
    return "Some kind of error, innit";
  }

}
