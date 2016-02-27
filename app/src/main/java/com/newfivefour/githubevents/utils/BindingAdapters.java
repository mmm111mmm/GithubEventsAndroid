package com.newfivefour.githubevents.utils;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class BindingAdapters {

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

      @Override
      public void onBitmapFailed(Drawable errorDrawable) { }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {
      }
    };
    willinglyStupid(t);
    Picasso with = Picasso.with(toolbar.getContext());
    with.load(s).into(t);
  }

  @BindingAdapter("app:clickabledestination")
  public static void getClickabledestination(final View v, final String s) {
    v.setClickable(true);
    v.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( s ) );
        Context context = v.getRootView().getContext();
        context.startActivity( browse );
      }
    });
  }

  @BindingAdapter("app:eventtime")
  public static void getEventime(TextView tv, String timeString) {
    tv.setText(Utils.getTimeAgoFromDateString(timeString));
  }

  @BindingAdapter("app:textIfNotBlank")
  public static void getTextIfNotBlank(TextView tv, String s) {
    if(tv.getText().length()==0) {
      tv.setText(s);
    }
  }


}
