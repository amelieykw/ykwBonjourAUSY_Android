package com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter;

import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by yukaiwen on 04/05/2017.
 */

public class BackgroundItemDecoration extends RecyclerView.ItemDecoration {

    private final int mOddBackground;
    private final int mEvenBackground;

    public BackgroundItemDecoration(@DrawableRes int oddBackground, @DrawableRes int evenBackground) {
        mOddBackground = oddBackground;
        mEvenBackground = evenBackground;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        view.setBackgroundColor(parent.getResources().getColor(position % 2 == 0 ? mEvenBackground : mOddBackground));
    }
}
