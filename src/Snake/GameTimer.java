package Snake;

import com.sun.javafx.binding.StringFormatter;

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
    private boolean isActive;

    public GameTimer () {
        minutes = 0;
        seconds = 0;
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

    public String getTime () {
        if (seconds > 60){
            int minutesToAdd = seconds / 60;
            minutes += minutesToAdd;
            seconds = seconds % 60;
        }
        return String.format("%02d:%02d %n", minutes, seconds);
    }

    public void startTimer () {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                seconds++;
                System.out.print(getTime());
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        isActive = true;
    }

    public void stopTimer() {
        timerTask.cancel();
        timer.cancel();
        timer.purge();

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
