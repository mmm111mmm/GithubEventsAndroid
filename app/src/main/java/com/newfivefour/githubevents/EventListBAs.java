package com.newfivefour.githubevents;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.newfivefour.githubevents.utls.Utils;

public class EventListBAs {

  @BindingAdapter("app:colourtimeago")
  public static void getColourtimeago(TextView v, String s) {
    String timeAgo = Utils.getTimeAgoFromDateString(s);
    if(timeAgo.contains("minute") || timeAgo.contains("seconds") || timeAgo.contains("hours")) {
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
