package com.yx.eventcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yx.eventcarlib.EventCar;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void goCallback(View view) {
        Log.i("yx", "post: "+"-------"+Thread.currentThread().getName());
        EventCar.getDefault().post(new User(26,"AD"));
    }
}
