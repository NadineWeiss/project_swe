package org.dhbw.swe.visualization;

import org.dhbw.swe.game.Observer;
import org.dhbw.swe.game.ObserverContext;

public interface Observable {

    void register(Observer observer);
    void remove(Observer observer);
    void notifyObservers(ObserverContext observerContext);


}
