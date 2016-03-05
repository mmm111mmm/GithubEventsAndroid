package com.newfivefour.githubevents;

import android.databinding.BindingAdapter;
import android.widget.EditText;

public class SettingsDialogViewBAs {

  @BindingAdapter("app:edittexterror")
  public static void getEdittexterror(EditText v, String error) {
    if(error!=null) {
      v.setError(error);
    } else {
      v.setError(null);
    }
  }

}
