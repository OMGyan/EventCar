package com.yx.eventcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yx.eventcarlib.EventCar;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void post(View view) {
        startActivity(new Intent(this,ThirdActivity.class));
        //  Log.i("yx", "post: "+"-------"+Thread.currentThread().getName());
        //  EventCar.getDefault().post(new User(34,"james"));
    }


}
