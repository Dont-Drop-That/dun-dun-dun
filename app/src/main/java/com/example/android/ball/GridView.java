package com.example.android.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by xuqingru on 12/7/15.
 */

public class GridView extends View {
    int mColumns, mRows;
    private int cells;
    private Paint mPaint = new Paint();
    private boolean[][] cellChecked;

    public GridView(Context context, int col, int rows, int w) {
        super(context);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(0xffdddddd); //light gray
        mPaint.setStrokeWidth(5);
        this.mColumns = col;
        this.mRows = rows;
        cells = w;
        cellChecked = new boolean[mColumns][mRows];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < mColumns; i++) {
            for (int j = 0; j < mRows; j++) {
                if (cellChecked[i][j])
                    canvas.drawRect(i * cells, j * cells, (i + 1) * cells, (j + 1) * cells, mPaint);
            }
        }

        for (int i = 1; i <= mColumns; i++) {
            canvas.drawLine(i * cells, 0, i * cells, height, mPaint);
        }

        for (int i = 1; i <= mRows; i++) {
            canvas.drawLine(0, i * cells, width, i * cells, mPaint);
        }
    }

}