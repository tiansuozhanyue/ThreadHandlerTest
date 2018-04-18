package com.example.administrator.threadhandlertest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

public class TestService extends Service {

    private Handler chiledThread;
    private Handler mainThread;
    private final int TEST00 = 0;
    private final int TEST01 = 1;

    public class TestBinder extends Binder {
        public TestService getIntence() {
            return TestService.this;
        }
    }

    private class ChiledThreadCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case TEST00:
                    logMsg("mainThread : test00 action in " + Thread.currentThread().getName());
                    break;
                case TEST01:
                    logMsg("mainThread : test01 action in " + Thread.currentThread().getName());
                    break;
            }
            return false;
        }
    }

    private class MainThreadCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case TEST00:
                    logMsg("childThread : test00 action in " + Thread.currentThread().getName());
                    break;
                case TEST01:
                    logMsg("childThread : test01 action in " + Thread.currentThread().getName());
                    break;
            }
            return false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TestBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HandlerThread thread = new HandlerThread("测试线程");
        thread.start();
        chiledThread = new Handler(thread.getLooper(), new ChiledThreadCallBack());

        mainThread = new Handler(getMainLooper(), new MainThreadCallBack());

        return super.onStartCommand(intent, flags, startId);
    }

    public void test00() {
        chiledThread.sendEmptyMessage(TEST00);
    }

    public void test01() {
        mainThread.sendEmptyMessage(TEST01);
    }

    private void logMsg(String info) {
        Log.i("ThreadTest", info);
    }

}
