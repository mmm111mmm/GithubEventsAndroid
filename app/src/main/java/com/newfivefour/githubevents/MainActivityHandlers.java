package com.newfivefour.githubevents;

import android.view.View;

import com.newfivefour.githubevents.server.ServerStuff;

public class MainActivityHandlers {

  public void onRefresh(View v) {
    AppState.appState.setLoading(true);
    new ServerStuff().stuff(v.getResources().getString(R.string.username));
  }

}
