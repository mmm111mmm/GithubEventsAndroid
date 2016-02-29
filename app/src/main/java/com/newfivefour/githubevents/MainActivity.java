package com.newfivefour.githubevents;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newfivefour.githubevents.databinding.MainActivityBinding;
import com.newfivefour.githubevents.server.ServerStuff;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainActivityBinding bd = DataBindingUtil.setContentView(this, R.layout.main_activity);
    setSupportActionBar(bd.toolbar);
    bd.setAppState(AppState.appState);
    bd.setHandlers(new MainActivityHandlers());
    new ServerStuff().stuff(getString(R.string.username));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    new MenuInflater(this).inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.option_changeusername:
        AppState.appState.setShowSettings(true);
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
