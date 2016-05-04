package com.newfivefour.githubevents;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.newfivefour.githubevents.logique.Actions;

public class DataBindingHandlers {

  public boolean onUsernameChange(TextView textView, int i, KeyEvent keyEvent) {
    if(i==EditorInfo.IME_ACTION_DONE) {
      Actions.changeUsername(textView.getText().toString());
    }
    return false;
  }

  public void onRefresh(View v) {
    Actions.refresh();
  }

}
