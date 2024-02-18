package lib.control;

import java.util.Timer;
import java.util.TimerTask;

class SimTimer {
    private Timer _timer;
    private long _currentTime, _totalTime;
    private String _timerSeconds;
    private Habitat _habitat;
    private boolean _isPaused, _isRunning;

    {
        _isPaused = _isRunning = false;
        _currentTime = 0;
        _timerSeconds = "Simulation hsn't started yet";
    }

    SimTimer() {
        _timer = new Timer();
    }

    void setHabitat(final Habitat hbt) { _habitat = hbt; }

    
    void start() {
        _habitat._controlPanel._startButton.setEnabled(false);
        _habitat._controlPanel._stopButton.setEnabled(true);
        _habitat._backToMenuButton.setEnabled(false);
        _timer = new Timer();
        _isRunning = true;
        System.out.println("\nSimulation has been started");
        _timer.schedule(new TimerTask() {
            public void run() {
                if (_currentTime != 0) { 
                    _timerSeconds = "Time: " + _currentTime / 1000 + " seconds";
                    if (_currentTime % _habitat._updateTime == 0) {
                        System.out.println("\n==============\nNEW GENERATION\n==============\n");
                        _habitat.update(_currentTime);
                    }
                }
                else _timerSeconds = "Time: 0 seconds";
                _habitat.setTimer(_timerSeconds);
                _currentTime += 1000;
                _habitat.moveEmployees();
            }
        }, 0, 1000);
    }
    
    void stop() {
        _habitat._controlPanel._startButton.setEnabled(true);
        _habitat._controlPanel._stopButton.setEnabled(false);
        _habitat._backToMenuButton.setEnabled(true);
        _isRunning = false;
        if (!_isPaused) _timer.cancel();
        _totalTime = _currentTime;
        _currentTime = 0;
        _timerSeconds = "Simulation ended";
        _habitat.setTimer(_timerSeconds);
        _habitat.clear();
    }

    long pause() {
        System.out.println("\nSimulation paused\n");
        _timer.cancel();
        _isPaused = true;
        return _currentTime;
    }

    void resume() {
        System.out.println("\nSimulation resumed\n");
        _timer = new Timer();
        _timer.schedule(new TimerTask() {
            public void run() {
                if (_currentTime != 0) { 
                    _timerSeconds = "Time: " + _currentTime / 1000 + " seconds";
                    if (_currentTime % _habitat._updateTime == 0) {
                        System.out.println("\n==============\nNEW GENERATION\n==============\n");
                        _habitat.update(_currentTime);
                    }
                }
                else _timerSeconds = "Time: 0 seconds";
                _habitat.setTimer(_timerSeconds);
                _currentTime += 1000;
                _habitat.moveEmployees();
            }
        }, 0, 1000);
        _isPaused = false;
    }

    long getTotalTime() { return _totalTime; }

    boolean isRunning() { return _isRunning; }
}
