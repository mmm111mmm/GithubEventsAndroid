package com.newfivefour.githubevents;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.newfivefour.githubevents.logique.AppState;
import com.newfivefour.githubevents.utils.Utils;

import java.util.ArrayList;

public class ErrorPopupView extends View {

  private CoordinatorLayout mCoordinatorLayout;
  private Snackbar mSnackBar;
  private ArrayList<AppState.Event> mEvents;

  public ErrorPopupView(Context context) {
    this(context, null, 0);
  }

  public ErrorPopupView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ErrorPopupView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  @NonNull
  private OnClickListener getDismissListener() {
    return new OnClickListener() {
      @Override
      public void onClick(View view) {
        mSnackBar.dismiss();
        mSnackBar = null;
      }};
  }

  public void setShow(boolean show) {
    Log.d("HIYA", "Showing popup: " + show);
    if(show && (mSnackBar==null)) {
      Log.d("HIYA", "Really showing");
      mCoordinatorLayout = Utils.scanForCoordinator(this);
      mSnackBar = Snackbar.make(mCoordinatorLayout, AppState.appState.getPopupError(), Snackbar.LENGTH_INDEFINITE);
      mSnackBar.setAction("Dismiss", getDismissListener());
      mSnackBar.show();
    } else if (!show && mSnackBar!=null && mSnackBar.isShown()) {
      Log.d("HIYA", "Really dismissing");
      mSnackBar.dismiss();
      mSnackBar = null;
    }
  }

}
