package com.yx.eventcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yx.eventcarlib.EventCar;
import com.yx.eventcarlib.Subscribe;
import com.yx.eventcarlib.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventCar.getDefault().register(this);
    }

    public void jump(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getInfo(User user){
        Log.i("yx", "getInfo: "+user.toString()+"-------"+Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCar.getDefault().unregister(this);
    }
}
