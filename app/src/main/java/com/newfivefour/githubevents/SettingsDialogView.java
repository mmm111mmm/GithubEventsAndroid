package com.newfivefour.githubevents;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.newfivefour.githubevents.logique.Actions;
import com.newfivefour.githubevents.logique.AppState;
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
    Fragment frag = supportFragmentManager.findFragmentByTag(UsernameDialogFragment.TAG);
    if (show && frag == null) {
      UsernameDialogFragment newFragment = UsernameDialogFragment.newInstance();
      newFragment.show(supportFragmentManager, UsernameDialogFragment.TAG);
    }
    if(frag==null || !(frag instanceof DialogFragment)){
      return;
    }
    DialogFragment fragment = (DialogFragment) frag;
    boolean v = frag.isVisible();
    if (show && !frag.isAdded()) {
      fragment.show(supportFragmentManager, UsernameDialogFragment.TAG);
    } else if (!show) {
      fragment.dismiss();
    }
  }

  public static class UsernameDialogFragment extends android.support.v4.app.DialogFragment {

    public static String TAG = "AUSERNAMEFRAGMENTDIALOG";

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
    public void onDismiss(DialogInterface dialog) {
      Actions.settings(false);
      super.onDismiss(dialog);
    }

    @Override
    public void onDestroyView() {
      // Used because of a bug in the support library
      if (getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
      super.onDestroyView();
    }
  }

}
