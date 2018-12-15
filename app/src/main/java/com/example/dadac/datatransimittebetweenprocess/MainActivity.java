package com.example.dadac.datatransimittebetweenprocess;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import fragment.FragmentTest1;
import fragment.FragmentTest2;
import modelofobserver.MyObserver;
import modelofobserver.MySubject;
import myobserver.Function;
import myobserver.ObservableManager;
import observer.ConcreteObserver;
import observer.ConcreteSubject;

import service.MySerice2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,FragmentTest1.MyCallBackListener {

    //定义Fragment管理器
    public FragmentManager fragmentManager;
    private FragmentTest1 fragmentTest1;
    private FragmentTest2 fragmentTest2;
    private Button DC_Button1_Fragment1;   //切换Fragment
    private Button DC_Button2_Fragment2;
    private Button DC_Button_ModelOfObserverCOPYFrag1;    //activity发数据给Fragemnt1
    private boolean Flag1 = false;
    private Button DC_Button_ModelOfObserverCOPYFrag2;    //activity发数据给Fragemnt2
    private boolean Flag2 = false;
    private Button DC_Button_ModelOfObserverSelf;         //自己实现的
    private Button DC_Button_Act2ActIntent;
    private Button DC_Button_Act2FragBundleFrag1;
    private Button DC_Button_Act2FragBundleFrag2;
    private Button DC_Button_BroadCast;                   //广播


    private int count = 0;
    private char achar = 'A';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        //假装这是开机默认显示的Fragment
        selectWhichFragmentToShow(1);
        initMainMenushow();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Flag1) {    //代码臃肿 建议用 handler messgae
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(IStatus.IStatus_Act2FragKey1, IStatus.IStatus_Act2FragValue1);
                    bundle1.putString(IStatus.IStatus_Act2FragKey2, IStatus.IStatus_Act2FragValue2);
                    bundle1.putString(IStatus.IStatus_Act2FragKey3, IStatus.IStatus_Act2FragValue3);
                    if (fragmentTest1 != null) {
                        fragmentTest1.setArguments(bundle1);
                        Flag1 = false;
                    }
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(IStatus.IStatus_Act2FragKey1, "Error");
                    bundle1.putString(IStatus.IStatus_Act2FragKey2, "Error");
                    bundle1.putString(IStatus.IStatus_Act2FragKey3, "Error");
                    if (fragmentTest1 != null) {
                        fragmentTest1.setArguments(bundle1);
                    }
                }
                if (Flag2) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(IStatus.IStatus_Act2FragKey1, IStatus.IStatus_Act2FragValue1);
                    bundle2.putString(IStatus.IStatus_Act2FragKey2, IStatus.IStatus_Act2FragValue2);
                    bundle2.putString(IStatus.IStatus_Act2FragKey3, IStatus.IStatus_Act2FragValue3);
                    if (fragmentTest2 != null) {
                        fragmentTest2.setArguments(bundle2);
                        Flag2 = false;
                    }
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(IStatus.IStatus_Act2FragKey1, "Error");
                    bundle2.putString(IStatus.IStatus_Act2FragKey2, "Error");
                    bundle2.putString(IStatus.IStatus_Act2FragKey3, "Error");
                    if (fragmentTest2 != null) {
                        fragmentTest2.setArguments(bundle2);
                    }
                }
            }
        }, 1, 1);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //生成两个一直动态变换的数据 1000周期
                count++;
                Log.i("dachen main", "count:" + count + "");
                if (achar == 'Z') {
                    achar = 'A';
                }
                achar = (char) (achar + 1);
                //                Log.i("dachen main", "tmp:" + String.valueOf(achar));  //char 转字符串
                //                Log.i("dachen main", "tmp:" + String.valueOf(new char[]{achar}));  //char 转字符串
                //                Log.i("dachen main", "tmp:" + Character.toString(achar));  //char 转字符串
                //                Log.i("dachen main", "tmp:" + new Character(achar).toString());  //char 转字符串
                //                Log.i("dachen main", "tmp:" + "" + achar);  //char 转字符串
                Log.i("dachen main", "tmp:" + new String(new char[]{achar}));  //char 转字符串

                // 若不想用button控制 或是说想一直发送数据  将 下面的注释取消就行了

              /*  ModelOfObsOffi();
                Object notify1 = ObservableManager.newInstance()
                        .notify(IStatus.IStatus_ObserverKey1, "使用观察者模式COPY传递数据:", count, achar);
                Object notify2 = ObservableManager.newInstance()
                        .notify(IStatus.IStatus_ObserverKey2, "使用观察者模式COPY传递数据:", count, achar);*/
            }
        }, 0, 1000);

        //ModelOfObserver();     自己的实现
        //ModelOfObserverOfficial();   官方自带的
        bindService(new Intent(this, MySerice2.class), serviceConnection2, BIND_AUTO_CREATE);
    }
    //2
    ServiceConnection serviceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service2) {
            MySerice2.Binder2 binder2 = (MySerice2.Binder2) service2;
            final MySerice2 mySerice2 = binder2.getService();
            mySerice2.setCallback2(new MySerice2.CallBack2() {
                @Override
                public void onDataChange2(String data) {
                   Log.i("dachen","接受来自Service的数据:"+data);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    //官方观察者模式启动
    private void ModelOfObsOffi() {
        //创建目标对象 Obserable
        MySubject subject = new MySubject();

        //创建观察者
        MyObserver myObserver1 = new MyObserver();
        subject.addObserver(myObserver1);
        subject.setObserverDataChange(String.valueOf(achar), count);  //把数据传出去
        Log.i("dachen", "使用观察者模式直接实现传递数据" + "code" + myObserver1.getCode() + ",powernumber:" + myObserver1.getPowerNumber());
    }

    //观察者模式测试 官方自带的接口
    private void ModelOfObserverOfficial() {
        //创建目标对象 Obserable
        observerofficial.ConcreteSubject subject = new observerofficial.ConcreteSubject();

        //创建观察者
        observerofficial.ConcreteObserver obs1 = new observerofficial.ConcreteObserver();
        observerofficial.ConcreteObserver obs2 = new observerofficial.ConcreteObserver();
        observerofficial.ConcreteObserver obs3 = new observerofficial.ConcreteObserver();

        //添加观察者  添加到 subject的容器中去
        subject.addObserver(obs1);
        subject.addObserver(obs2);
        subject.addObserver(obs3);
        //改变 subject 的状态
        subject.set(100);
        Log.i("dachen", obs1.getMyState() + "");
        Log.i("dachen", obs2.getMyState() + "");
        Log.i("dachen", obs3.getMyState() + "");
    }

    //观察者模式测试
    private void ModelOfObserver() {
        //创建目标对象
        ConcreteSubject subject = new ConcreteSubject();

        //创建多个观察者
        ConcreteObserver concreteObserver1 = new ConcreteObserver();
        ConcreteObserver concreteObserver2 = new ConcreteObserver();
        ConcreteObserver concreteObserver3 = new ConcreteObserver();

        //添加观察者  添加到 subject的容器中去
        subject.registerNewObserver(concreteObserver1);
        subject.registerNewObserver(concreteObserver2);
        subject.registerNewObserver(concreteObserver3);

        //改变 subject 的状态
        subject.setState(3000);
        Log.i("dachen", concreteObserver1.getMyState() + "");
        Log.i("dachen", concreteObserver2.getMyState() + "");
        Log.i("dachen", concreteObserver3.getMyState() + "");

        subject.setState(30);
        Log.i("dachen", concreteObserver1.getMyState() + "");
        Log.i("dachen", concreteObserver2.getMyState() + "");
        Log.i("dachen", concreteObserver3.getMyState() + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DC_Button1_Fragment1:
                selectWhichFragmentToShow(1);
                break;
            case R.id.DC_Button2_Fragment2:
                selectWhichFragmentToShow(2);
                break;
            case R.id.DC_Button_ModelOfObserverCOPYFrag1:
                Object notify1 = ObservableManager.newInstance()
                        .notify(IStatus.IStatus_ObserverKey1, "使用观察者模式COPY传递数据:", count, achar);
                break;
            case R.id.DC_Button_ModelOfObserverCOPYFrag2:
                Object notify2 = ObservableManager.newInstance()
                        .notify(IStatus.IStatus_ObserverKey2, "使用观察者模式COPY传递数据:", count, achar);
                break;
            case R.id.DC_Button_ModelOfObserverSelf:
                ModelOfObsOffi();
                break;
            case R.id.DC_Button_Act2ActIntent:
                //这种方式 没什么卵用，根本不靠谱
                Intent intent1 = new Intent(this, BroadcastActivity.class);
                intent1.putExtra(IStatus.IStatus_Act2ActKey, IStatus.IStatus_Act2Actvalue);
                startActivity(intent1);
                break;
            case R.id.DC_Button_Act2FragBundleFrag1:
                /*这种方式传递数据，必须一直传递数据 不然 Fragment会报空指针异常 建议必须直接放在线程里面一直发送才行*/
                /*Bundle bundle1 = new Bundle();   看线程
                bundle1.putString(IStatus.IStatus_Act2FragKey1, IStatus.IStatus_Act2FragValue1);
                bundle1.putString(IStatus.IStatus_Act2FragKey2, IStatus.IStatus_Act2FragValue2);
                bundle1.putString(IStatus.IStatus_Act2FragKey3, IStatus.IStatus_Act2FragValue3);
                if (fragmentTest1 != null) {
                    fragmentTest1.setArguments(bundle1);
                }*/
                Flag1 = true;

                break;
            case R.id.DC_Button_Act2FragBundleFrag2:
                /*这种方式传递数据，必须一直传递数据 不然 Fragment会报空指针异常 建议必须直接放在线程里面一直发送才行*/
                /*Bundle bundle2 = new Bundle();
                bundle2.putString(IStatus.IStatus_Act2FragKey1, IStatus.IStatus_Act2FragValue1);
                bundle2.putString(IStatus.IStatus_Act2FragKey2, IStatus.IStatus_Act2FragValue2);
                bundle2.putString(IStatus.IStatus_Act2FragKey3, IStatus.IStatus_Act2FragValue3);
                if (fragmentTest2 != null) {
                    fragmentTest2.setArguments(bundle2);
                }*/
                Flag2 = true;
                break;
            case R.id.DC_Button_BroadCast:
                //看我的另外一篇博客  BroadCast的两种使用方法  讲的很详细了
                //https://blog.csdn.net/dadachenchen/article/details/83044862
                break;
            default:
                break;
        }
    }

    //界面初始化
    private void initMainMenushow() {
        DC_Button1_Fragment1 = (Button) findViewById(R.id.DC_Button1_Fragment1);
        DC_Button1_Fragment1.setOnClickListener(this);

        DC_Button2_Fragment2 = (Button) findViewById(R.id.DC_Button2_Fragment2);
        DC_Button2_Fragment2.setOnClickListener(this);

        DC_Button_ModelOfObserverCOPYFrag1 = (Button) findViewById(R.id.DC_Button_ModelOfObserverCOPYFrag1);
        DC_Button_ModelOfObserverCOPYFrag1.setOnClickListener(this);

        DC_Button_ModelOfObserverCOPYFrag2 = (Button) findViewById(R.id.DC_Button_ModelOfObserverCOPYFrag2);
        DC_Button_ModelOfObserverCOPYFrag2.setOnClickListener(this);

        DC_Button_ModelOfObserverSelf = (Button) findViewById(R.id.DC_Button_ModelOfObserverSelf);
        DC_Button_ModelOfObserverSelf.setOnClickListener(this);

        DC_Button_Act2ActIntent = (Button) findViewById(R.id.DC_Button_Act2ActIntent);
        DC_Button_Act2ActIntent.setOnClickListener(this);

        DC_Button_Act2FragBundleFrag1 = (Button) findViewById(R.id.DC_Button_Act2FragBundleFrag1);
        DC_Button_Act2FragBundleFrag1.setOnClickListener(this);

        DC_Button_Act2FragBundleFrag2 = (Button) findViewById(R.id.DC_Button_Act2FragBundleFrag2);
        DC_Button_Act2FragBundleFrag2.setOnClickListener(this);

        DC_Button_BroadCast = (Button) findViewById(R.id.DC_Button_BroadCast);
        DC_Button_BroadCast.setOnClickListener(this);
    }

    //根据 index 选择哪个 Fragment 来显示，
    public void selectWhichFragmentToShow(int index) {
        FragmentTransaction myfragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(myfragmentTransaction);
        switch (index) {
            case 1:
                if (fragmentTest1 == null) {
                    fragmentTest1 = new FragmentTest1();
                    myfragmentTransaction.add(R.id.DC_Fragemnt_ShowMain,
                            fragmentTest1);
                } else {
                    myfragmentTransaction.show(fragmentTest1);
                    fragmentTest1.onResume();
                }
                myfragmentTransaction.commit();
                break;
            case 2:
                if (fragmentTest2 == null) {
                    fragmentTest2 = new FragmentTest2();
                    myfragmentTransaction.add(R.id.DC_Fragemnt_ShowMain,
                            fragmentTest2);
                } else {
                    myfragmentTransaction.show(fragmentTest2);
                    fragmentTest2.onResume();
                }
                myfragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    public void hideFragments(FragmentTransaction myfragmentTransaction) {
        if (fragmentTest1 != null) {
            myfragmentTransaction.hide(fragmentTest1);
        }
        if (fragmentTest2 != null) {
            myfragmentTransaction.hide(fragmentTest2);
        }
    }

    @Override
    public void onDataChange2(String data) {
        Log.i("dachen","回调接受的数据是："+data);
    }
}
