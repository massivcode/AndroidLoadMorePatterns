package com.massivcode.loadmorepatterns.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.loadmorepatterns.R;
import com.massivcode.loadmorepatterns.adapters.DummyItemAdapter;
import com.massivcode.loadmorepatterns.backend.FakeBackend;
import com.massivcode.loadmorepatterns.backend.FakeBackend.OnResponseListener;
import com.massivcode.loadmorepatterns.models.DummyItem;
import java.util.ArrayList;

public class ScrollUpToLoadMoreItemsActivity extends AppCompatActivity {

  @BindView(R.id.rv)
  RecyclerView mRecyclerView;

  @BindView(R.id.init_data_loading_container)
  LinearLayout mInitDataLoadingContainer;

  private DummyItemAdapter mDummyItemAdapter;
  private int mCurrentPage = 0;
  private boolean mIsReachedLastPages = false;
  private FakeBackend mFakeBackend;
  private LinearLayoutManager mLinearLayoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scroll_up_to_load_more_items);
    ButterKnife.bind(this);
    mFakeBackend = new FakeBackend();

    initRecyclerView();
  }

  private void initRecyclerView() {
    mDummyItemAdapter = new DummyItemAdapter();
    mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    mRecyclerView.setAdapter(mDummyItemAdapter);
    mRecyclerView.addOnScrollListener(mOnRecyclerViewScrollListener);

    mFakeBackend.requestDummyItem(mCurrentPage, new OnResponseListener() {
      @Override
      public void onSuccess(ArrayList<DummyItem> items) {
        mInitDataLoadingContainer.setVisibility(View.GONE);
        mDummyItemAdapter.setItems(items);
      }

      @Override
      public void onFailure(int responseCode) {

      }
    });
  }

  private RecyclerView.OnScrollListener mOnRecyclerViewScrollListener = new OnScrollListener() {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);

      if (mIsReachedLastPages) {
        return;
      }

      if (mDummyItemAdapter.isProgress()) {
        return;
      }

      if (mLinearLayoutManager.findFirstVisibleItemPosition() + 1
          >= mDummyItemAdapter.getItemCount() - mRecyclerView.getChildCount()) {

        mCurrentPage += 1;
        mDummyItemAdapter.setIsProgress(true);

        mFakeBackend.requestDummyItem(mCurrentPage, new OnResponseListener() {
          @Override
          public void onSuccess(ArrayList<DummyItem> items) {
            mDummyItemAdapter.setIsProgress(false);
            mDummyItemAdapter.addItems(items);
          }

          @Override
          public void onFailure(int responseCode) {
            if (responseCode == 404) {
              mIsReachedLastPages = true;
            }

            mDummyItemAdapter.setIsProgress(false);
          }
        });
      }

    }
  };
}
