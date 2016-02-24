package com.newfivefour.githubevents;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.newfivefour.githubevents.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainActivityBinding bd = DataBindingUtil.setContentView(this, R.layout.main_activity);
    setSupportActionBar(bd.toolbar);
    bd.setAppState(new AppState());
  }

}
