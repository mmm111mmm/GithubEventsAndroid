package com.newfivefour.githubevents.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

public class ScrollAwareFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
  public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
    super();
  }

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
    return dependency instanceof Snackbar.SnackbarLayout;
  }

  @Override
  public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                     final View directTargetChild, final View target, final int nestedScrollAxes) {
    return true;
  }

  @Override
  public void onNestedScroll(final CoordinatorLayout coordinatorLayout,
                             final FloatingActionButton child,
                             final View target, final int dxConsumed, final int dyConsumed,
                             final int dxUnconsumed, final int dyUnconsumed) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
      child.hide();
    } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
      child.show();
    }
  }

  @Override
  public void onDependentViewRemoved(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
    super.onDependentViewRemoved(parent, child, dependency);
    if (dependency instanceof Snackbar.SnackbarLayout) {
      float translationY = Math.min(0, parent.getBottom() - child.getBottom());
      child.setTranslationY(translationY);
    } else {
    }
  }

  @Override
  public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
    if (dependency instanceof Snackbar.SnackbarLayout) {
      float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
      child.setTranslationY(translationY);
      return true;
    } else {
      return false;
    }
  }

}
