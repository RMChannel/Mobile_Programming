package com.esame.citoroberto;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CursorView extends View {

    // direzioni per il movimento
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;

    private Runnable winCallback;

    public void setWinCallback(Runnable cb) {
        this.winCallback = cb;
    }

    private Paint borderPaint;
    private Paint cursorPaint;
    private Paint obsPaint;
    private Paint goalPaint;

    private float cx, cy;
    private float r; // raggio del cursore
    private float step;

    private int side;

    private List<RectF> obstacles = new ArrayList<>();
    private RectF goal;
    private Random rng = new Random();

    private boolean won = false;

    public CursorView(Context context) {
        this(context, null);
    }

    public CursorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    private void initPaints() {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dpToPx(3));

        cursorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cursorPaint.setColor(Color.RED);
        cursorPaint.setStyle(Paint.Style.FILL);

        obsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        obsPaint.setColor(Color.BLUE);
        obsPaint.setStyle(Paint.Style.FILL);

        goalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        goalPaint.setColor(Color.argb(100, 0, 200, 0));
        goalPaint.setStyle(Paint.Style.FILL);

        r = dpToPx(15);
        step = dpToPx(20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // area quadrata, 80% della larghezza dello schermo
        DisplayMetrics dm = getResources().getDisplayMetrics();
        side = (int) (dm.widthPixels * 0.8f);
        setMeasuredDimension(side, side);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float border = borderPaint.getStrokeWidth();
        float goalH = dpToPx(40);
        goal = new RectF(border, border, w - border, border + goalH);

        // posizione iniziale: centro in basso
        cx = w / 2f;
        cy = h - r - border;

        generateObstacles(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();
        float half = borderPaint.getStrokeWidth() / 2f;

        // bordo dell'area di gioco
        canvas.drawRect(half, half, w - half, h - half, borderPaint);

        if (goal != null) {
            canvas.drawRect(goal, goalPaint);
        }

        for (RectF obs : obstacles) {
            canvas.drawRect(obs, obsPaint);
        }

        canvas.drawCircle(cx, cy, r, cursorPaint);
    }

    // metodo unico per muovere il cursore nelle 4 direzioni
    public void move(int dir) {
        if (won)
            return;

        float oldX = cx;
        float oldY = cy;

        switch (dir) {
            case DIR_UP:
                cy -= step;
                break;
            case DIR_DOWN:
                cy += step;
                break;
            case DIR_LEFT:
                cx -= step;
                break;
            case DIR_RIGHT:
                cx += step;
                break;
        }

        clamp();

        // se collide con un ostacolo torno alla posizione precedente
        if (collides()) {
            cx = oldX;
            cy = oldY;
        }

        invalidate();

        // controllo se il cursore ha raggiunto la zona verde
        if (!won && goal != null && cy - r <= goal.bottom) {
            won = true;
            if (winCallback != null)
                winCallback.run();
        }
    }

    private boolean collides() {
        RectF cr = new RectF(cx - r, cy - r, cx + r, cy + r);
        for (RectF obs : obstacles) {
            if (RectF.intersects(cr, obs))
                return true;
        }
        return false;
    }

    public void resetGame() {
        won = false;
        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0)
            return;

        float border = borderPaint.getStrokeWidth();
        cx = w / 2f;
        cy = h - r - border;

        generateObstacles(w, h);
        invalidate();
    }

    private void generateObstacles(int w, int h) {
        obstacles.clear();

        float border = borderPaint.getStrokeWidth();
        float goalH = dpToPx(40);
        float safeBottom = dpToPx(50); // zona sicura in basso dove nasce il cursore

        float minY = border + goalH;
        float maxY = h - border - safeBottom;

        // genero 10 ostacoli con dimensioni e posizioni casuali
        for (int i = 0; i < 10; i++) {
            float ow = dpToPx(30) + rng.nextFloat() * dpToPx(50);
            float oh = dpToPx(15) + rng.nextFloat() * dpToPx(25);
            float left = border + rng.nextFloat() * (w - 2 * border - ow);
            float top = minY + rng.nextFloat() * (maxY - minY - oh);
            obstacles.add(new RectF(left, top, left + ow, top + oh));
        }
    }

    private void clamp() {
        float border = borderPaint.getStrokeWidth();
        float min = r + border;
        float maxX = getWidth() - r - border;
        float maxY = getHeight() - r - border;

        if (cx < min)
            cx = min;
        if (cx > maxX)
            cx = maxX;
        if (cy < min)
            cy = min;
        if (cy > maxY)
            cy = maxY;
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
