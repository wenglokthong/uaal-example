package com.unity.mynativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.company.product.OverrideUnityActivity;
public class MainUnityActivity extends OverrideUnityActivity {
    // Setup activity layout
    static String result;
    int width = 300;
    int height = 100;
    //static Button textbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControlsToUnityFrame();
        Intent intent = getIntent();
        handleIntent(intent);
        if (MainActivity.testee)
        {
            mUnityPlayer.UnitySendMessage("SceneLoader", "LoadTesteeScene", "11|5|5|0|2|3|2|2|3.7|1.4|5.2|1|1|0.5195873");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if(intent == null || intent.getExtras() == null) return;

        if(intent.getExtras().containsKey("doQuit"))
            if(mUnityPlayer != null) {
                finish();
            }
    }

    @Override
    protected void showMainActivity(String setToColor) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("setColor", setToColor);
        startActivity(intent);
    }

    @Override public void onUnityPlayerUnloaded()
    {
        showMainActivity("");
    }
    public static void receiveResult(String value)
    {
        result = "";//Clear old data
        result = value; //Get new one
        Log.d("RECEIVE", "receiveResult: " + value);
    }
    public void addControlsToUnityFrame() {
        FrameLayout layout = mUnityPlayer;
        {
            Button myButton = new Button(this);
            myButton.setText("Instructor");
            myButton.setX(10);
            myButton.setY(200);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   //showMainActivity("");
                    mUnityPlayer.UnitySendMessage("SceneLoader", "LoadTesterScene", "");
                }
            });
            layout.addView(myButton, width, height);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Trainee");
            myButton.setX(320);
            myButton.setY(200);
            myButton.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.UnitySendMessage("SceneLoader", "LoadTesteeScene", "11|5|5|0|2|3|2|2|3.7|1.4|5.2|1|1|0.5195873");
                }
            });
            layout.addView(myButton, width, height);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Unload");
            myButton.setX(630);
            myButton.setY(200);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mUnityPlayer.unload();
                }
            });
            layout.addView(myButton, width, height);
        }

        {
            Button myButton = new Button(this);
            myButton.setText("Finish");
            myButton.setX(630);
            myButton.setY(500);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            layout.addView(myButton, width, height);
        }

        //{
        //    textbutton = new Button(this);
        //    textbutton.setText("unloaded");
        //    textbutton.setX(320);
        //    textbutton.setY(800);
        //    layout.addView(textbutton, 300, 200);
        //}
    }


}
