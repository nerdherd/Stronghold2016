package org.camsrobotics.lib;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A separate timed thread that gets called periodically
 * 
 * @author Wesley
 *
 */
public class Looper {
	
	private double period = 1.0 / 100.0;
    protected Loopable loopable;
    private Timer looperUpdater;
    protected String m_name;

    /**
     * Default constructor
     * 
     * @param name
     * @param loopable
     * @param period
     */
    public Looper(String name, Loopable loopable, double period) {
        this.period = period;
        this.loopable = loopable;
        this.m_name = name;
    }

    private class Task extends TimerTask {

        private Looper looper;

        public Task(Looper looper) {
            if (looper == null) {
                throw new NullPointerException("Given Looper was null");
            }
            this.looper = looper;
        }

        public void run() {
            looper.update();
        }
    }

    /**
     * Start the thread
     */
    public void start() {
        if (looperUpdater == null) {
            looperUpdater = new Timer("Looper - " + this.m_name);
            looperUpdater.scheduleAtFixedRate(new Task(this), 0L, (long) (this.period * 1000));
        }
    }

    /**
     * Stop the thread
     */
    public void stop() {
        if (looperUpdater != null) {
            looperUpdater.cancel();
            looperUpdater = null;
        }
    }

    private void update() {
        loopable.update();
    }
}
