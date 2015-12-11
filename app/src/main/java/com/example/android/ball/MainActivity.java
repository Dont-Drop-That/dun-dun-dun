package com.example.android.ball;


import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorEventListener;
import android.widget.ImageButton;

import java.util.Random;


public class MainActivity extends Activity implements View.OnClickListener{
    int radius, radiusHole, cells, c , counter;
    BallView mBallView = null;
    HoleView mHoleView1 = null;
   // HoleView mHoleView2 = null;
    GridView gridView = null;
    Handler RedrawHandler = new Handler();//so redraw occurs in main thread
    Handler holeHandler = new Handler();
    Timer mTmr = null;
    TimerTask mTsk = null;
    int mScrWidth, mScrHeight;
    android.graphics.PointF mBallPos, mBallSpd, mHolePos;
    ImageButton setting;
    //long starttime = 0;
    private HoleView hole1, hole2, hole3, hole4, hole5;
    private HoleView[] holeList = {hole1, hole2, hole3, hole4, hole5};
    private Timer holeTimer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
        getWindow().setFlags(0xFFFFFFFF,
                LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create pointer to main screen
        final FrameLayout mainView = (android.widget.FrameLayout) findViewById(R.id.main_view);


        //get screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();
        mBallPos = new android.graphics.PointF();
        mBallSpd = new android.graphics.PointF();

        //create grid background
        cells = mScrWidth / 10;
        int rows = mScrHeight / cells;
        gridView = new GridView(this, 10, rows, cells);


        //create variables for ball position and speed
        mBallPos.x = mScrWidth / 2;
        mBallPos.y = mScrHeight / 2;
        mBallSpd.x = 0;
        mBallSpd.y = 0;

        //The ball occupies 2% of the screen
        radius = (mScrHeight * mScrWidth) / 23040;

        //create initial ball
        mBallView = new BallView(this, mBallPos.x, mBallPos.y, radius);


        //the Hole is 1.5 times bigger than the ball
        radiusHole = 2 * radius;
        mainView.addView(gridView);//add background to main screen

        //create initial Holes &
        counter = 0;
        for (int i = 0; i < 5; i++) {
            Random randx = new Random();
            Random randy = new Random();
            mHolePos = new android.graphics.PointF();
            //set the Hole inside the screen
            holeList[i] = new HoleView(this, mScrWidth, mScrHeight, radiusHole);//making the hole
            //this next part is for checking whether or not there is a hole overlapping, if so make holes until they don't, do this for all holes
            if (i != 0) {
                for (int j = 0; j < i; j++) {
                    long startTimer = 0;
                    boolean check = true;
                    int d = (int) Math.sqrt((holeList[i].mX - holeList[j].mX) * (holeList[i].mX - holeList[j].mX) + (holeList[i].mY - holeList[j].mY) * (holeList[i].mY - holeList[j].mY));
                    //  while (check) {
                    if (d < (2 * holeList[i].mR)) {
                        //Random newx = new Random();
                        //Random newy = new Random();
                        //int px = newx.nextInt(mScrWidth - holeList[i].mR * 2 + 1) + holeList[i].mR;
                        //int py = newy.nextInt(mScrHeight - holeList[i].mR * 2 + 1) + holeList[i].mR;
                        //holeList[i].mX = px;
                        //holeList[i].mY = py;//making the hole
                        //holeList[i].mPaint.setColor(0xffffaa11);
                        holeList[j].setVisibility(View.GONE);
                        /* Handler colorh = new Handler();
                        colorh.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 4; i++) {
                                    holeList[i].mPaint.setColor(0xff222222);
                                }
                            }
                        }, 1000);*/
                    } else {
                        check = false;
                    }
                    //int dis_origin = (int) Math.sqrt((holeList[i].mX * holeList[i].mX) + (holeList[i].mY * holeList[i].mY));
                    /*if(dis_origin<holeList[i].mR && holeList[i].getVisibility()==View.VISIBLE){
                        Random newx = new Random();
                        Random newy = new Random();
                        int px = newx.nextInt(mScrWidth - holeList[i].mR * 2 + 1) + holeList[i].mR;
                        int py = newy.nextInt(mScrHeight - holeList[i].mR * 2 + 1) + holeList[i].mR;
                        holeList[i].mX = px;
                        holeList[i].mY = py;
                    }*/
                    //}


                }
                if (holeList[i].getVisibility() == View.VISIBLE) {
                    counter++;
                }

            }


            //holeList[i].setVisibility(View.VISIBLE);
            mainView.addView(holeList[i]); //add hole to main screen
            //holeList[i].invalidate();
            //public void run(){
            // }
            //mainView.removeView(holeList[i]);
            //changecolor(counter);
            //mainView.addView(holeList[i]);
            //for(int i =0;i<counter;i++){
            //    mainView.addView(holeList[i]);
            //   holeList[i].invalidate();
        }
        for(int i = 0;i<5;i++){
            if(holeList[i].getVisibility()==View.VISIBLE){
                mainView.removeView(holeList[i]);
                changecolor(counter);
                mainView.addView(holeList[i]);
            }
        }


        mainView.addView(mBallView); //add ball to main screen
        mBallView.invalidate(); //call onDraw in BallView


        //setup setting button
        setting=(ImageButton)findViewById(R.id.setting_button2);
        setting.setImageResource(R.drawable.settings);
        setting.setVisibility(View.VISIBLE);
        setting.setOnClickListener(this);

       // mainView.addView(mHoleView1); //add hole to main screen
      //  mHoleView1.invalidate();//call onDraw in HoleView

        /*Get time elaspsed since Hole appears. If it passed 1 sec, Hole color changes to black
        mTmr = new Timer();
        mTsk = new TimerTask() {
            @Override
            public void run() {
                holeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        counter ++;
                        if (counter == 999999999){
                            for(int i=0; i<5; i++)
                            holeList[i].mPaint.setColor(0xff000000);
                         }
                    }
                });
            }
        };
        mTmr.schedule(mTsk,0,1000);*/
        //mHoleView1 = new HoleView(this,mScrWidth,mScrHeight,radiusHole,c);


        //listener for accelerometer, use anonymous class for simplicity
                ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).registerListener(
                new SensorEventListener() {

                    @Override
                    public void onSensorChanged(SensorEvent event) {

                        //set ball speed based on phone tilt (ignore Z axis)
                        mBallSpd.x = -event.values[0] * 2;
                        mBallSpd.y = event.values[1] * 2;

                        //if the ball is inside of the hole, the ball should disappear/"falling into" the hole
                        for (int i=0;i<5;i++) {
                            int distance = (int) Math.sqrt((holeList[i].mX - mBallPos.x) * (holeList[i].mX - mBallPos.x) + (holeList[i].mY - mBallPos.y) * (holeList[i].mY - mBallPos.y));
                            if ((radiusHole > distance)&&holeList[i].getVisibility()==View.VISIBLE) {
                                mBallSpd.x = 0;
                                mBallSpd.y = 0;//unreference the ball if it falls into the hole
                            }
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    } //ignore this event
                },
                ((SensorManager) getSystemService(Context.SENSOR_SERVICE))
                        .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_GAME);

    } //OnCreate

    //@Override
    public void changecolor(int size){
        try{
            Thread.sleep(1000);
            for(int i = 0; i < size; i++) {
                holeList[i].mPaint.setColor(0xff222222);
            }

        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    //For state flow see http://developer.android.com/reference/android/app/Activity.html
    @Override
    public void onPause() //app moved to background, stop background threads
    {
        mTmr.cancel(); //kill\release timer (our only background thread)
        mTmr = null;
        mTsk = null;
        super.onPause();
    }

    @Override
    public void onResume() //app moved to foreground (also occurs at app startup)
    {
        //create timer to move ball to new position
        mTmr = new Timer();
        mTsk = new TimerTask() {
            public void run() {
                //if debugging with external device,
                //  a cat log viewer will be needed on the device
                android.util.Log.d(
                        "TiltBall","Timer Hit - " + mBallPos.x + ":" + mBallPos.y);
                //move ball based on current speed
                float tempx = mBallPos.x + mBallSpd.x;
                float tempy = mBallPos.y + mBallSpd.y;

                //setup the boundary of the screen
                if (tempx - radius > 0 && tempx + radius < mScrWidth) {
                    mBallPos.x = tempx;
                }
                if (tempy - radius > 0 && tempy + radius < mScrHeight) {
                    mBallPos.y = tempy;
                }
                //update ball class instance
                mBallView.mX = mBallPos.x;
                mBallView.mY = mBallPos.y;
                //redraw ball. Must run in background thread to prevent thread lock.
                RedrawHandler.post(new Runnable() {
                    public void run() {
                        mBallView.invalidate();
                    }});
            }}; // TimerTask

        mTmr.schedule(mTsk,10,10); //start timer
        super.onResume();
    } // onResume

    @Override
    public void onDestroy() //main thread stopped
    {
        super.onDestroy();
        System.runFinalizersOnExit(true); //wait for threads to exit before clearing app
        android.os.Process.killProcess(android.os.Process.myPid());  //remove app from memory
    }

    //listener for config change.
    //This is called when user tilts phone enough to trigger landscape view
    //we want our app to stay in portrait view, so bypass event
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

}