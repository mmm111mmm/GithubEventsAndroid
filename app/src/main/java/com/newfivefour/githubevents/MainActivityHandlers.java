package com.newfivefour.githubevents;

import android.view.View;

import com.newfivefour.githubevents.server.ServerStuff;

public class MainActivityHandlers {

  public int thing = 1;
  public void onRefresh(View v) {
    new ServerStuff().stuff(v.getResources().getString(R.string.username));
  }

}
