package com.massivcode.loadmorepatterns.backend;

import android.os.Handler;
import android.os.Looper;
import com.massivcode.loadmorepatterns.models.DummyItem;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by massivcode@gmail.com on 2017. 10. 24. 09:42
 */

public class FakeBackend {

  private static final long RESPONSE_TIME = 2000;
  private static final long LAST_PAGE = 4;
  private static final int ITEM_PER_PAGE = 10;

  private Handler mRequestHandler = new Handler(Looper.getMainLooper());
  private AtomicInteger mIdGenerator = new AtomicInteger(1);

  public interface OnResponseListener {

    void onSuccess(ArrayList<DummyItem> items);

    void onFailure(int responseCode);

  }

  public void requestDummyItem(final int page, final OnResponseListener onResponseListener) {
    if (page == 0) {
      mIdGenerator.set(1);
    }

    if (page == LAST_PAGE) {
      onResponseListener.onFailure(404);
      return;
    }

    mRequestHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        onResponseListener.onSuccess(generateDummyItems());
      }
    }, RESPONSE_TIME);
  }

  private ArrayList<DummyItem> generateDummyItems() {
    ArrayList<DummyItem> dummyItems = new ArrayList<>();
    Random random = new Random();

    for (int i = 0; i < ITEM_PER_PAGE; i++) {
      int itemId = mIdGenerator.getAndAdd(1);

      dummyItems.add(new DummyItem
          (
              itemId,
              "Dummy title " + itemId,
              "Dummy Contents " + itemId,
              "Author " + itemId,
              generateRandomImage(random)
          ));
    }

    return dummyItems;
  }

  private String generateRandomImage(Random random) {
    String baseUrl = "https://raw.githubusercontent.com/prChoe/Medias/master/images/cat_";
    int randomInt = random.nextInt(19) + 1;
    return baseUrl + randomInt + ".jpg";
  }
}
