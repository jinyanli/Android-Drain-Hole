package com.ucsc.jin.asg5;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.util.DebugUtils;

/**
 * TODO: document your custom view class.
 */
public class Screen extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    Tomato tom=new Tomato();
    float accelx;
    float accely;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public Screen(Context context) {
        super(context);
        post(animator);
        //init(null, 0);
    }

    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init(attrs, 0);
    }

    public Screen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //radius
        tom.r=20;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        //background color
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        //internal wall
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        //first internal wall
        canvas.drawLine(0, 300, 400, 300, paint);
        //second internal wall
        canvas.drawLine(getWidth(),700,100,700,paint);
        //drain hole
        paint.setColor(Color.GRAY);
        canvas.drawCircle(500, 950, (float) 1.5 * tom.r, paint);
        //tomato
        paint.setColor(Color.RED);
        canvas.drawCircle(tom.x, tom.y, tom.r, paint);

    }

    private Runnable animator=new Runnable() {
        @Override
        public void run() {
            physics();
            postDelayed(this,20);
            invalidate();

        }
    };
    public void physics(){

        //acceleration
        tom.velx+=accelx*0.01;
        tom.x+=tom.velx*1;
        tom.vely+=accely*0.01;
        tom.y+=tom.vely*1;
        //top wall
        if(tom.y-tom.r<0){
            tom.vely= (float) (tom.vely*(-0.5));
            tom.y=0+tom.r;
        }
        //button wall
        if(tom.y+1*tom.r>getHeight()){
            tom.vely= (float) (tom.vely*(-0.5));
            tom.y=getHeight()-1*tom.r;
        }
        //left wall
        if(tom.x-tom.r<0){
            tom.velx= (float) (tom.velx*(-0.5));
            tom.x=tom.r;
        }
       //right wall
        if(tom.x+1*tom.r>getWidth()){
            tom.velx= (float) (tom.velx*(-0.5));
            tom.x=getWidth()-1*tom.r;
        }
        //Log.i("tom.y + tom.r",String.valueOf(tom.y + tom.r));
        //physics for upper internal wall
             if (tom.x < 400) {
                 if (Math.abs(tom.y - 298)<tom.r) {
                     Log.i("tom.y + tom.r",String.valueOf((int)(tom.y + tom.r)));
                     tom.vely = (float) (tom.vely*(-0.5));
                     tom.y = 298 - tom.r;

                 }
                 else if (Math.abs(tom.y - 304)<tom.r) {

                     Log.i("tom.y - tom.r",String.valueOf((int)(tom.y - tom.r)));
                     tom.vely =(float) (tom.vely*(-0.5));
                     tom.y = 304 + tom.r;
                 }

             }
        //end point for upper partition
        if(400<tom.x&&(Math.sqrt(Math.pow((400 - tom.x),2)+Math.pow((300-tom.y),2))<tom.r)){
            tom.vely=(float) (tom.vely*(-1));
            tom.velx=(float) (tom.velx*(-1));
        }

        //physics for lower internal wall
        if (tom.x > 100){
            if (Math.abs(tom.y - 698)<tom.r) {
                Log.i("tom.y + tom.r",String.valueOf((int)(tom.y + tom.r)));
                tom.vely = (float) (tom.vely*(-0.5));
                tom.y = 698 - tom.r;

            }else
            if (Math.abs(tom.y - 702)<tom.r) {

                Log.i("tom.y - tom.r",String.valueOf((int)(tom.y - tom.r)));
                tom.vely =(float) (tom.vely*(-0.5));
                tom.y = 702 + tom.r;
            }
        }

        //end point for lower partition
        if(tom.x<100&&Math.sqrt(Math.pow((100 - tom.x),2)+Math.pow((700-tom.y),2))<tom.r){
            tom.vely=(float) (tom.vely*(-1));
            tom.velx=(float) (tom.velx*(-1));
        }

        //drain hole
        if(Math.sqrt(Math.pow((500 - tom.x),2)+Math.pow((950-tom.y),2))<(1.5 * tom.r-tom.r)){
                tom.x=99;
                tom.y=50;
         }

        }






    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
