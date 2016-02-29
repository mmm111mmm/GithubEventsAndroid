package com.newfivefour.githubevents;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.newfivefour.githubevents.server.ServerStuff;
import com.newfivefour.githubevents.utils.Utils;

public class SettingsDialogHandlers {
  public boolean onUsernameChange(TextView textView, int i, KeyEvent keyEvent) {
    if(i==EditorInfo.IME_ACTION_DONE) {
      new ServerStuff().stuff(textView.getText().toString());
      AppState.appState.setShowSettings(false);
      Utils.dismissDialogByTag(textView.getRootView().getContext(), "tag");
    }
    return false;
  }

}
