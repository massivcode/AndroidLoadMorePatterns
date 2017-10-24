package com.massivcode.loadmorepatterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.massivcode.loadmorepatterns.activities.ScrollUpAndSwipeDownToLoadMoreItemsActivity;
import com.massivcode.loadmorepatterns.activities.ScrollUpToLoadMoreItemsActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.scrollUpToLoadMoreItemsBtn)
  protected void onScrollUpToLoadMoreItemsButtonClicked(View view) {
    startActivity(new Intent(MainActivity.this, ScrollUpToLoadMoreItemsActivity.class));
  }

  @OnClick(R.id.scrollUpAndSwipeDownToLoadMoreItemsButton)
  protected void onScrollUpAndSwipeDownToLoadMoreItemsButtonClicked(View view) {
    startActivity(new Intent(MainActivity.this, ScrollUpAndSwipeDownToLoadMoreItemsActivity.class));
  }


}
