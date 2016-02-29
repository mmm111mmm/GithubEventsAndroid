package com.newfivefour.githubevents;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;

import com.newfivefour.githubevents.databinding.UsernameDialogBinding;
import com.newfivefour.githubevents.utils.Utils;

import java.util.ArrayList;

public class SettingsDialogView extends View {

  private ArrayList<AppState.Event> mEvents;

  public SettingsDialogView(Context context) {
    this(context, null, 0);
  }

  public SettingsDialogView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SettingsDialogView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void show(boolean show) {
    Activity activity = Utils.scanForActivity(getContext());
    if(activity==null || !(activity instanceof FragmentActivity)) {
      return;
    }
    FragmentManager supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
    Fragment fragment = supportFragmentManager.findFragmentByTag("tag");
    if (show && fragment == null) {
      UsernameDialogFragment newFragment = UsernameDialogFragment.newInstance();
      newFragment.show(supportFragmentManager, "tag");
    } else if (show && fragment instanceof DialogFragment) {
      ((DialogFragment) fragment).show(supportFragmentManager, "tag");
    } else if (!show && fragment!=null && (fragment instanceof DialogFragment)) {
      ((DialogFragment) fragment).dismiss();
    }
  }

  public static class UsernameDialogFragment extends android.support.v4.app.DialogFragment {

    public static UsernameDialogFragment newInstance() {
      UsernameDialogFragment dialogue = new UsernameDialogFragment();
      return dialogue;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      setRetainInstance(true);
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      UsernameDialogBinding dialog = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.username_dialog, null, false);
      dialog.setVariable(com.newfivefour.githubevents.BR.appState, AppState.appState);
      dialog.setVariable(com.newfivefour.githubevents.BR.handlers, new SettingsDialogHandlers());
      builder.setView(dialog.getRoot());
      return builder.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
      // Used because of a bug in the support library
      if (getDialog() != null && getRetainInstance())
        getDialog().setDismissMessage(null);
      super.onDestroyView();
    }
  }

}
