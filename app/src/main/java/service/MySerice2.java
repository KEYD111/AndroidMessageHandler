package service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @ Create by dadac on 2018/9/14.
 * @Function: 最简单的 service 的测试
 * @Return:
 */
public class MySerice2 extends Service {

    private boolean connecting = false;
    private CallBack2 callBack2;


    @Override
    public IBinder onBind(Intent intent) {
        return new Binder2();
    }


    //全部返回回去
    public class Binder2 extends Binder {
        public MySerice2 getService() {
            return MySerice2.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connecting = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (connecting == true) {
                    i++;
                    if (callBack2 != null) {
                        callBack2.onDataChange2(i + "");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setCallback2(CallBack2 callback2) {
        this.callBack2 = callback2;

    }

    public static interface CallBack2 {
        void onDataChange2(String data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connecting = false;
    }


}
