package observerofficial;

import java.util.Observable;
import java.util.Observer;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function: 观察者
 * @Return:
 */
public class ConcreteObserver implements Observer {

    private int myState;

    public int getMyState() {
        return myState;
    }

    public void setMyState(int myState) {
        this.myState = myState;
    }

    @Override
    public void update(Observable o, Object arg) {
        myState = ((ConcreteSubject) o).getState();
    }
}
