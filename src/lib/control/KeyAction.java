package lib.control;

import lib.employee.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class KeyAction implements KeyListener {
    private int keyCode;
    private SimTimer _timer;
    private Habitat _hbt;
    private boolean _started;

    void setHabitat(final Habitat hbt) { 
        int upt = Habitat.findUpdateTime(Developer.getGenerateTime(), Manager.getGenerateTime());
        _timer = new SimTimer(upt);
        _hbt = hbt;
        _timer.setHabitat(hbt); 
        _started = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        System.out.println("Key pressed: " + keyCode);
        if (keyCode == 66) { if (!_started) _timer.start(); _started = true; } //start
        else if (keyCode == 69) { if (_started) _timer.stop(); _started = false; } //end
        else if (keyCode == 84) { _hbt._timerLabel.setVisible(_hbt._labelHidden); _hbt._labelHidden = !_hbt._labelHidden;  } //hide timer
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
