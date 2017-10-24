package com.massivcode.loadmorepatterns.loaders;

import android.widget.ImageView;

/**
 * Created by massivcode@gmail.com on 2017. 10. 24. 10:09
 */

public interface ImageLoader {
  void onLoadImage(ImageView targetImageView, String imageUrl);
}
