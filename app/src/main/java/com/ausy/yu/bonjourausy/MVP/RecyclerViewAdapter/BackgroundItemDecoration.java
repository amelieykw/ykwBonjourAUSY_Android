package com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter;

import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ausy.yu.bonjourausy.R;


/**
 * Created by yukaiwen on 04/05/2017.
 */

public class BackgroundItemDecoration extends RecyclerView.ItemDecoration {

    private final int mOddBackground;
    private final int mEvenBackground;
    private int positionOfDejaPriseEnCharge;
    private int positionOfLastItem;
    private String modeType;

    public BackgroundItemDecoration(@DrawableRes int oddBackground, @DrawableRes int evenBackground, String modeType, int positionOfDejaPriseEnCharge, int positionOfLastItem) {
        mOddBackground = oddBackground;
        mEvenBackground = evenBackground;
        this.positionOfDejaPriseEnCharge = positionOfDejaPriseEnCharge;
        this.positionOfLastItem = positionOfLastItem;
        this.modeType = modeType;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);

        Log.d("position : ", positionOfDejaPriseEnCharge+"");

        if(modeType == "Manager") {
            view.setBackgroundColor(parent.getResources().getColor(position % 2 == 0 ? mEvenBackground : mOddBackground));
        }
        if(modeType == "Candidat") {
            if(position < positionOfDejaPriseEnCharge) {
                view.setBackgroundColor(parent.getResources().getColor(position % 2 == 0 ? mEvenBackground : mOddBackground));
            } else if(position <= positionOfLastItem){
                view.setBackgroundColor(parent.getResources().getColor(R.color.color_item_blocked));
                view.setClickable(false);
            }
        }
    }
}
