package Snake;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by filip on 02.05.2016.
 */
public class GameTimer {
    private int minutes;
    private int seconds;
    private Timer timer;
    private TimerTask timerTask;
    private volatile boolean isActive;

    public GameTimer () {
        this.minutes = 0;
        this.seconds = 0;
    }

    public GameTimer(int minutes, int seconds){
        if (seconds > 60){
            int minutesToAdd = seconds / 60;
            this.minutes = minutes + minutesToAdd;
            this.seconds = seconds % 60;
        } else {
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }
    
    void secondsInc()
    { this.seconds++; }

    public String getTime () {
        if (seconds > 60){
            int minutesToAdd = seconds / 60;
            minutes += minutesToAdd;
            seconds = seconds % 60;
        }
        return String.format("%02d:%02d %n", minutes, seconds);
    }

    public void startTimer () {
        this.seconds = 0;
        timer = new Timer();
        timerTask = new TimerTask() {
           @Override
           public void run() {
               secondsInc();
               System.out.print(getTime());
           }
        };        
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        isActive = true;
    }

    public void stopTimer() {
        System.out.println("Stopping timer.");
        timerTask.cancel();      
        System.out.println("timerTask.cancel()");
        timer.cancel();
        System.out.println("timer.cancel()");
        timer.purge();
        System.out.println("timer.purge()");

        isActive = false;
    }

    public void delayTimer (int mililiseconds) {
        try {
            Thread.sleep(mililiseconds);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean getIsActive () {
        return isActive;
    }

}
