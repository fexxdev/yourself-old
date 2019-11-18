package com.creativity.yourself;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import android.os.Handler;
import android.media.MediaPlayer;


public class TypeWriter extends AppCompatTextView {


    private int i = 0;
    private Runnable press_button;
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 100; // in ms

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();

    private Runnable characterAdder = new Runnable() {


        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++) + "_");

            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            } else
                press_button();
        }
    };
    private void press_button() {
        press_button = new Runnable() {
            @Override
            public void run() {
                if (i <= 10) {
                    if (i++ % 2 != 0) {
                        setText(mText + "_");
                        mHandler.postDelayed(press_button, 300);
                    } else {
                        setText(mText + " ");
                        mHandler.postDelayed(press_button, 300);
                    }
                } else
                    i = 0;
            }
        };
        mHandler.postDelayed(press_button, 300);
    }

        public void animateText(CharSequence txt) {
            mText = txt;
            mIndex = 0;

            setText("");
            mHandler.removeCallbacks(characterAdder);
            mHandler.postDelayed(characterAdder, mDelay);
        }

        public void setCharacterDelay(long m) {
            mDelay = m;
        }


    }




