package com.newfivefour.githubevents;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newfivefour.githubevents.utils.Utils;

import java.util.ArrayList;

public class EventListViewBAs {

  @BindingAdapter("app:events")
  public static void getEvents(EventsListView v, ArrayList<AppState.Event> o) {
    Log.d("HIYA", "In getEvents with " + new Gson().toJson(o));
    v.setEvents(o);
  }

  @BindingAdapter("app:colourtimeago")
  public static void getColourtimeago(TextView v, String s) {
    String timeAgo = Utils.getTimeAgoFromDateString(s);
    if(timeAgo.contains("minute") || timeAgo.contains("second") || timeAgo.contains("hour")) {
      v.setTextColor(v.getResources().getColor(android.R.color.holo_green_dark));
    } else {
      v.setTextColor(v.getResources().getColor(R.color.greytext));
    }
  }

  @BindingAdapter("app:typestring")
  public static void getTypestring(TextView tv, String s) {
    switch (s) {
      case "PushEvent":
        tv.setText("Pushed to");
        break;
      case "WatchEvent":
        tv.setText("Watching");
        break;
      case "IssueCommentEvent":
        tv.setText("Commented on");
        break;
      case "IssuesEvent":
        tv.setText("Created issue for");
        break;
      case "CreateEvent":
        tv.setText("Created ");
        break;
      default:
        tv.setText(s);
    }
  }

}