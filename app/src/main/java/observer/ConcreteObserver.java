package observer;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 实现接口
 * @Return:
 */
public class ConcreteObserver implements Observer {
    public int getMyState() {
        return myState;
    }

    public void setMyState(int myState) {
        this.myState = myState;
    }

    private int myState;   //myState需要跟目标对象的state值保持一致

    @Override
    public void update(Subject1 subject1) {
        myState = ((ConcreteSubject) subject1).getState();
    }


}
