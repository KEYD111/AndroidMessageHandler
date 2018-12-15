package fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.dadac.datatransimittebetweenprocess.IStatus;
import com.example.dadac.datatransimittebetweenprocess.R;

import java.util.Timer;
import java.util.TimerTask;

import myobserver.Function;
import myobserver.ObservableManager;


/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 测试 2
 * @Return:
 */
public class FragmentTest1 extends Fragment implements Function {

    private TextView DC_TextView_ShowPrevious1;
    private TextView DC_TextView_ShowLast1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObservableManager.newInstance().registerObserver(IStatus.IStatus_ObserverKey1, this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    i++;
                    if (myCallBackListener != null) {
                        myCallBackListener.onDataChange2(i + "");
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

    //通过回调函数传递值
    private MyCallBackListener myCallBackListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myCallBackListener = (MyCallBackListener) context;
    }

    public interface MyCallBackListener {
        public abstract void onDataChange2(String data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View FragemntTest1View = inflater.inflate(R.layout.fragment_test1, container, false);
        DC_TextView_ShowPrevious1 = (TextView) FragemntTest1View.findViewById(R.id.DC_TextView_ShowPrevious1);
        DC_TextView_ShowLast1 = (TextView) FragemntTest1View.findViewById(R.id.DC_TextView_ShowLast1);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(0x01);   // 接受Activity发送的消息  Bundle 的方式
                myHandler.sendEmptyMessage(0x02);   // 观察者设计模式
                myHandler.sendEmptyMessage(0x03);   // 广播的方法
                myHandler.sendEmptyMessage(0x04);   // Eventbus的方法
            }
        }, 0, 1);
        return FragemntTest1View;
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    if (isAdded()) {
                        if (!getArguments().getString(IStatus.IStatus_Act2FragKey1).equals("Error")) {
                            String a1 = getArguments().getString(IStatus.IStatus_Act2FragKey1);
                            String a2 = getArguments().getString(IStatus.IStatus_Act2FragKey2);
                            String a3 = getArguments().getString(IStatus.IStatus_Act2FragKey3);
                            Log.i("dachen", "Frag1  Bundle方式接受来自Activity的数据为 " + "a1:" + a1 + ",a2:" + a2 + ",a3:" + a3);
                        }
                    }
                    break;
                case 0x02:

                    break;
                case 0x03:

                    break;
                case 0x04:

                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public Object function(Object[] data) {
        Log.i("dachen fragment1", String.valueOf(data[0]) + "," + String.valueOf(data[1]));
        return "我是fragment1的返回结果";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ObservableManager.newInstance().removeObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ObservableManager.newInstance().removeObserver(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ObservableManager.newInstance().removeObserver(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ObservableManager.newInstance().removeObserver(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            ObservableManager.newInstance().removeObserver(this);
        } else
            ObservableManager.newInstance().registerObserver("MAINSEND1", this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ObservableManager.newInstance().registerObserver("MAINSEND1", this);
    }


}




























