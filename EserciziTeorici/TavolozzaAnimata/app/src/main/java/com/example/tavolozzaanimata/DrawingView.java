package com.example.tavolozzaanimata;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {
    private List<Cerchio> cerchi =new ArrayList<>();
    private Paint paint;

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Cerchio c : cerchi) {
            paint.setColor(c.getColore());
            canvas.drawCircle(c.getX(), c.getY(), 50f, paint);
            //canvas.drawRect(c.getX(),c.getY(),c.getX()+100,c.getY()+100,paint); Disegna rettangolo
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Intercettiamo il momento in cui il dito tocca lo schermo
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cerchi.add(new Cerchio(event.getX(), event.getY(),Color.BLUE));

            // Richiede il ridisegno della View (chiama onDraw)
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void clearPoints() {
        cerchi.clear();
        invalidate(); // Importante: ridisegna la vista vuota
    }
}
