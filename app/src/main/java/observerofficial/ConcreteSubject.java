package observerofficial;

import java.util.Observable;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 定义目标对象
 * @Return:
 */
public class ConcreteSubject extends Observable {
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void set(int s) {
        state = s;    //目标对象的状态发生了改变

        setChanged();   //表示目标对象已经做了更改
        notifyObservers(state);   //通知所有的观察者发生了变化

    }


}
