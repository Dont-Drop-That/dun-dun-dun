package com.example.android.ball;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class start extends Activity implements View.OnClickListener {
    GridView gridView = null;
    int mScrWidth, mScrHeight, cells;
    ImageButton play, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
        getWindow().setFlags(0xFFFFFFFF,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final RelativeLayout mainView = (android.widget.RelativeLayout) findViewById(R.id.start);


        //get screen dimension
        Display display = getWindowManager().getDefaultDisplay();
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();

        //create grid background
        cells = mScrWidth / 10;
        int rows = mScrHeight / cells;
        gridView = new GridView(this,10,rows,cells);

        //create play buttons
        play=(ImageButton)findViewById(R.id.play_button);
        play.setImageResource(R.drawable.play);
        play.setVisibility(View.VISIBLE);
        play.setOnClickListener(this);

        //create setting buttons
        settings=(ImageButton)findViewById(R.id.setting_button);
        settings.setImageResource(R.drawable.settings);
        settings.setVisibility(View.VISIBLE);
        settings.setOnClickListener(this);
        //mainView.addView(gridView);//add background to main screen

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.play_button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_button:
                Intent intent1 = new Intent(this, Setting.class);
                startActivity(intent1);
                break;
        }
    }
}
