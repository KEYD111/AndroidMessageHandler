package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 类的对象   主题对象   目标对象  父类
 * @Return:
 */
public class Subject1 {

    protected List<Observer> list = new ArrayList<>();  //存放观察主题对象的所有的观察者

    public void registerNewObserver(Observer obs) {   //增加新的观察者
        list.add(obs);
    }

    public void removeOldObserver(Observer obs) {  //删除旧的观察者
        list.remove(obs);
    }

    //通知所有的观察者更新状态
    public void notifyAllObservers() {
        for (Observer obs : list) {
            obs.update(this);
        }
    }


}
