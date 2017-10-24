package com.massivcode.loadmorepatterns.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.massivcode.loadmorepatterns.R;
import com.massivcode.loadmorepatterns.loaders.GlideImageLoader;
import com.massivcode.loadmorepatterns.loaders.ImageLoader;
import com.massivcode.loadmorepatterns.models.DummyItem;
import java.util.ArrayList;

/**
 * Created by massivcode@gmail.com on 2017. 10. 24. 10:29
 */

public class DummyItemAdapter extends RecyclerView.Adapter<ViewHolder> {

  private static final int VIEW_TYPE_ITEM = 0;
  private static final int VIEW_TYPE_PROGRESS = 1;

  private ArrayList<DummyItem> mItems;
  private Context mContext;
  private ImageLoader mImageLoader;

  private boolean mIsProgress = false;
  private Handler mHandler = new Handler(Looper.getMainLooper());

  @Override
  public int getItemViewType(int position) {
    return mItems.get(position) == null ? VIEW_TYPE_PROGRESS : VIEW_TYPE_ITEM;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    mImageLoader = new GlideImageLoader(mContext);

    ViewHolder holder = null;

    if (viewType == VIEW_TYPE_ITEM) {
      holder =
          new DummyItemViewHolder(
              LayoutInflater.from(mContext).inflate(R.layout.item_dummy, parent, false));
    } else if (viewType == VIEW_TYPE_PROGRESS) {
      holder =
          new LoadMoreProgressViewHolder(
              LayoutInflater.from(mContext).inflate(R.layout.item_progress, parent, false));
    }

    return holder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if (holder instanceof DummyItemViewHolder) {
      ((DummyItemViewHolder) holder).bindItem(mItems.get(position), mImageLoader);
    }
  }

  @Override
  public int getItemCount() {
    return mItems == null ? 0 : mItems.size();
  }

  public void setItems(ArrayList<DummyItem> items) {
    mItems = items;
    notifyDataSetChanged();
  }

  public void addItems(final ArrayList<DummyItem> items) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        if (mItems == null) {
          setItems(items);
        } else {
          int oldItemLastPosition = mItems.size();
          mItems.addAll(items);
          notifyItemRangeInserted(oldItemLastPosition, items.size());
        }
      }
    });
  }

  public boolean isProgress() {
    return mIsProgress;
  }

  public void setIsProgress(boolean isProgress) {
    mIsProgress = isProgress;

    mHandler.post(new Runnable() {
      @Override
      public void run() {
        if (mIsProgress) {
          mItems.add(null);
          notifyItemInserted(mItems.size() - 1);
        } else {
          mItems.remove(null);
          notifyItemRemoved(mItems.size());
        }
      }
    });
  }

  static class DummyItemViewHolder extends ViewHolder {

    @BindView(R.id.title_tv)
    TextView mTitleTextView;
    @BindView(R.id.contents_tv)
    TextView mContentsTextView;
    @BindView(R.id.author_tv)
    TextView mAuthorTextView;
    @BindView(R.id.profile_iv)
    ImageView mProfileImageView;

    DummyItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindItem(DummyItem dummyItem, ImageLoader imageLoader) {
      mTitleTextView.setText(dummyItem.getTitle());
      mContentsTextView.setText(dummyItem.getContents());
      mAuthorTextView.setText(dummyItem.getAuthor());
      imageLoader.onLoadImage(mProfileImageView, dummyItem.getProfileImageUrl());
    }
  }

  static class LoadMoreProgressViewHolder extends ViewHolder {

    @BindView(R.id.pb)
    ProgressBar mProgressBar;

    LoadMoreProgressViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
