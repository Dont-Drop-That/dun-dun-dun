package com.example.android.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

/**
 * Created by xuqingru on 12/6/15.
 */
public class HoleView extends View {
    public float mX;
    public float mY;
    public int color;
    public final int mR;
    public final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //construct new ball object
    public HoleView(Context context, int mScrWidth, int mScrHeight, int r) {
        super(context);
        //color hex is [transparency][red][green][blue]
        //this.color = c;
        //if(color == 1)
          mPaint.setColor(0xffcccccc);//color is grey
        //else if(color == 2)
          // mPaint.setColor(0xff222222);//color is black

        Random x = new Random();
        Random y = new Random();
        int posx = x.nextInt(mScrWidth - r * 2 + 1) + r;
        int posy = y.nextInt(mScrHeight - r * 2 + 1) + r;
        this.mX = posx;
        this.mY = posy;


        this.mR = r; //radius
    }

    //called by invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mR, mPaint);
    }
}
