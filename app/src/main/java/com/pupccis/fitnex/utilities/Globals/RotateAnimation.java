package com.pupccis.fitnex.utilities.Globals;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pupccis.fitnex.R;

public class RotateAnimation {
    public static void rotateAnimation(Context context, ImageView imageView){
        Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        imageView.setImageResource(R.drawable.ic_close_button);
        imageView.startAnimation(rotateAnimation);
    }
}
