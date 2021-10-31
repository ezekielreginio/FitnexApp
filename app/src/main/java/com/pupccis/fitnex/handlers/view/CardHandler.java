package com.pupccis.fitnex.handlers.view;

import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.pupccis.fitnex.R;

public class CardHandler {

    public static void hideIndicator(SwipeRevealLayout swipeRevealLayout, ImageView imageView){
        swipeRevealLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
            @Override
            public void onClosed(SwipeRevealLayout view) {
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onOpened(SwipeRevealLayout view) {
                imageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSlide(SwipeRevealLayout view, float slideOffset) {

            }
        });
    }
    public static void swipeableCardOnClickHide(ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, SwipeRevealLayout swipeRevealLayout, ImageView imageView){
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRevealLayout.close(true);
                switch (constraintLayout2.getVisibility()){
                    case View.GONE:
                        constraintLayout2.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.ic_expand_more);

                        break;
                    case View.VISIBLE:
                        constraintLayout2.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.ic_expand_less);
                        break;
                }
            }
        });
    }
}
