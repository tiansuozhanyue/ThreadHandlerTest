package com.example.administrator.threadhandlertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    TestService testService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, TestService.class);

        startService();

        bindService();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_00:
                if (testService != null)
                    testService.test00();
                break;
            case R.id.send_01:
                if (testService != null)
                    testService.test01();
                break;
        }

    }

    private void startService() {
        startService(intent);
    }

    private void closeService() {
        stopService(intent);
    }

    private void bindService() {
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            testService = ((TestService.TestBinder) service).getIntence();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            testService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeService();
    }

}
