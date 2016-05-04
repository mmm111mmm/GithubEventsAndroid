package com.newfivefour.githubevents;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.newfivefour.githubevents.utils.Utils;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DataBindingAdapters {

  @BindingAdapter("app:edittexterror")
  public static void getEdittexterror(EditText v, String error) {
    if (error != null) {
      v.setError(error);
    } else {
      v.setError(null);
    }
  }

  @BindingAdapter("app:colourtimeago")
  public static void getColourtimeago(TextView v, String s) {
    String timeAgo = Utils.getTimeAgoFromDateString(s);
    if (timeAgo.contains("recent") || timeAgo.contains("minute") || timeAgo.contains("second") || timeAgo.contains("hour")) {
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

  public static Target staticTargetBecausePicassoIsBroken;

  public static void willinglyStupid(Target t) {
    staticTargetBecausePicassoIsBroken = t;
  }

  public static void willinglyUnStupid() {
    staticTargetBecausePicassoIsBroken = null;
  }

  @BindingAdapter("app:loadingimage")
  public static void setLoadingimage(final Toolbar toolbar, String s) {
    Target t = new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Bitmap b = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
        BitmapDrawable icon = new BitmapDrawable(toolbar.getResources(), b);
        toolbar.setNavigationIcon(icon);
        willinglyUnStupid();
      }
      @Override public void onBitmapFailed(Drawable errorDrawable) { }
      @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
    };
    if (s != null && s.length() > 0) {
      willinglyStupid(t);
      new Picasso.Builder(toolbar.getContext())
              .downloader(new OkHttpDownloader(toolbar.getContext(), Integer.MAX_VALUE))
              .build()
              .load(s)
              .into(t);
    }
  }

  @BindingAdapter("app:clickabledestination")
  public static void getClickabledestination(final View v, final String s) {
    v.setClickable(true);
    if (s != null && s.length() > 0) {
      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
          Context context = v.getRootView().getContext();
          context.startActivity(browse);
        }
      });
    }
  }

  @BindingAdapter("app:eventtime")
  public static void getEventime(TextView tv, String timeString) {
    tv.setText(Utils.getTimeAgoFromDateString(timeString));
  }

  @BindingAdapter("app:textIfNotBlank")
  public static void getTextIfNotBlank(TextView tv, String s) {
    if (tv.getText().length() == 0) {
      tv.setText(s);
    }
  }

}
