package com.newfivefour.githubevents;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.newfivefour.githubevents.databinding.UsernameDialogBinding;

public class UsernameDialogFragment extends android.support.v4.app.DialogFragment {

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
    dialog.setVariable(com.newfivefour.githubevents.BR.handlers, new MainActivityHandlers());
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

