package lib.control;

import lib.employee.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyAction implements KeyListener {
    private int keyCode;
    private SimTimer _timer;
    private Habitat _hbt;

    void setHabitat(final Habitat hbt) { 
        int upt = Habitat.findUpdateTime(Developer.getGenerateTime(), Manager.getGenerateTime());
        _timer = new SimTimer(upt);
        _hbt = hbt;
        _timer.setHabitat(hbt); 
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        System.out.println("Key pressed: " + keyCode);
        if (keyCode == 66) { _timer.start(); } //start
        else if (keyCode == 69) { _timer.stop(); } //end
        else if (keyCode == 84) { _hbt._timerLabel.setVisible(_hbt._labelHidden); _hbt._labelHidden = !_hbt._labelHidden;  } //hide timer
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
