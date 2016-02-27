package com.newfivefour.githubevents;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.newfivefour.githubevents.server.ServerStuff;

public class MainActivityHandlers {

  public void onRefresh(View v) {
    AppState.appState.setLoading(true);
    new ServerStuff().stuff(v.getResources().getString(R.string.username));
  }

  public boolean onEditor(TextView textView, int i, KeyEvent keyEvent) {
    if(i==EditorInfo.IME_ACTION_DONE) {
      new ServerStuff().stuff(textView.getText().toString());
      AppState.appState.setShowSettings(false);
    }
    return false;
  }

}
