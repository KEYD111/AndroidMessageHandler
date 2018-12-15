package modelofobserver;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 观察者
 * @Return:
 */
public class MyObserver implements Observer {

    private String code;   //代码值
    private int powerNumber;       //电量

    public int getPowerNumber() {
        return powerNumber;
    }

    public void setPowerNumber(int powerNumber) {
        this.powerNumber = powerNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void update(Observable o, Object arg) {
        code = ((MySubject) o).getCode();
        powerNumber = ((MySubject) o).getPowerNumber();
        Log.i("dachen", "自己实现的观察者 Observer:" + "code" + code + ",Powernumber" + powerNumber);
    }
}
