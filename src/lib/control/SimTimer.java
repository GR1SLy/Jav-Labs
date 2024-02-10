package lib.control;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import lib.employee.*;

public class SimTimer {
    private Timer _timer;
    private int _updateTime;
    private long _startTime, _currentTime;
    private boolean _started;
    private String _timerSeconds;
    private Habitat _habitat;
    {
        _startTime = _currentTime = 0;
        _started = false;
        _timerSeconds = "Simulation hsn't started yet";
    }

    SimTimer(final int updateTime) {
        _timer = new Timer();
        _updateTime = updateTime * 1000;
    }

    void setHabitat(final Habitat hbt) { _habitat = hbt; }

    void start() {
        if (_started) _timer = new Timer();
        _started = true;
        _startTime = System.currentTimeMillis();
        System.out.println("\nSimulation has been started");
        _timer.schedule(new TimerTask() {
            public void run() {
                if (_currentTime != 0) { 
                    _timerSeconds = "Time: " + _currentTime / 1000 + " seconds";
                    if (_currentTime % _updateTime == 0) {
                        System.out.println("\n==============\nNEW GENERATION\n==============\n");
                        _habitat.update(_currentTime);
                    }
                }
                else _timerSeconds = "Time: 0 seconds";
                _habitat.setTimer(_timerSeconds);
                _currentTime += 1000;
            }
        }, 0, 1000);
    }
    
    void stop() {
        _timer.cancel();
        _currentTime = 0;
        _timerSeconds = "Simulation ended";
        _habitat.setTimer(_timerSeconds);
        long finishTime = System.currentTimeMillis() - _startTime;
        String statistics = "\n\n=============\nSimulation cancelled\n=============\n\nSimulation time: " + finishTime + 
        "\nCurrent statistics:\nDevelopers generated: " + Developer.getCount() + 
        "\nManagers generated: " + Manager.getCount() + "\nTotal Employees generated: " + Employee.getCount();
        System.out.println(statistics);
        _habitat.clear();
        JOptionPane.showMessageDialog(null, statistics, "Statistics", 1);
    }
}
