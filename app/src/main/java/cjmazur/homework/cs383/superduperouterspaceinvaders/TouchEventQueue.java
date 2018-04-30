package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.view.MotionEvent;

import java.util.LinkedList;

/**
 * Created by CJ on 4/30/2018.
 */

class TouchEventQueue {

    private static TouchEventQueue defaultInstance;

    public static TouchEventQueue getInstance() {
        if (defaultInstance == null)
            defaultInstance = new TouchEventQueue();
        return defaultInstance;
    }

    private LinkedList<MotionEvent> events;

    public TouchEventQueue() {
        events = new LinkedList<>();
    }

    public synchronized void enqueue(MotionEvent e) {
        events.addLast(e);
    }

    public synchronized MotionEvent dequeue() {
        if (events.isEmpty())
            return null;
        else
            return events.removeFirst();
    }

}
