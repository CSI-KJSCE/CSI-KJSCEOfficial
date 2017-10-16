
package org.csikjsce.csi_kjsceofficial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sumit on 26/9/17.
 */

public class Custom_textview extends android.support.v7.widget.AppCompatTextView {

        public Custom_textview(Context context) {
            super(context);
            Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/msyi.ttf");
            this.setTypeface(face);
        }

        public Custom_textview(Context context, AttributeSet attrs) {
            super(context, attrs);
            Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/msyi.ttf");
            this.setTypeface(face);
        }

        public Custom_textview(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/msyi.ttf");
            this.setTypeface(face);
        }

        protected void onDraw (Canvas canvas) {
            super.onDraw(canvas);


        }


}
