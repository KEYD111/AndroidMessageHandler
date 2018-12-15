package observer;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 具体的类继续抽象类
 * @Return:
 */
public class ConcreteSubject extends Subject1 {

    private int state;    //具体的方法

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        this.notifyAllObservers();    //目标对象的值发生改变通知所有的观察者
    }
}
