package com.massivcode.loadmorepatterns.loaders;

import android.content.Context;
import android.widget.ImageView;
import com.massivcode.loadmorepatterns.R;

/**
 * Created by massivcode@gmail.com on 2017. 10. 24. 10:10
 */

public class GlideImageLoader implements ImageLoader {

  private Context mContext;

  public GlideImageLoader(Context mContext) {
    this.mContext = mContext.getApplicationContext();
  }

  @Override
  public void onLoadImage(ImageView targetImageView, String imageUrl) {
    GlideApp
        .with(mContext)
        .load(imageUrl)
        .placeholder(R.mipmap.ic_launcher_round)
        .fallback(R.mipmap.ic_launcher)
        .into(targetImageView);
  }
}
