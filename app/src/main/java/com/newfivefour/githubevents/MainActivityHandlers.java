package com.newfivefour.githubevents;

import android.view.View;

import com.newfivefour.githubevents.logique.Actions;

public class MainActivityHandlers {

  public void onRefresh(View v) {
    Actions.send("REFRESH");
  }

}
