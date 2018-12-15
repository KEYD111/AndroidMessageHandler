package modelofobserver;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 对象  继承自带的父类
 * @Return:
 */
public class MySubject extends Observable {

    //新建两个数据
    private String code;   //代码值
    private int powerNumber;       //电量

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPowerNumber() {
        return powerNumber;
    }

    public void setPowerNumber(int powerNumber) {
        this.powerNumber = powerNumber;
    }


    //外部改变数据的方法
    public void setObserverDataChange(String code1, int powerNumber1) {
        code = code1;
        powerNumber = powerNumber1;
        //如果仅仅是传递一个值 可以直接放notifyObservers（） 随便什么类型
        //但是我就是要传递两个值   就得先转换
        List<String> lists = new ArrayList<String>();
        lists.add(code);
        lists.add(powerNumber + "");
        setChanged();     //表示目标对象已经做了更改
        notifyObservers(lists);
        lists = new ArrayList<String>();  //释放内存
        Log.i("dachen","Subject 数据发生改变");
    }


}


















