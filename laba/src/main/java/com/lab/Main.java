package com.lab;

import com.lab.lib.control.Habitat;

public class Main {
    public static void main(String[] args) {
        Habitat hbt = new Habitat(1400, 800);
        hbt.setTimerAcceleration(1);
        hbt.setAIV(10);
        hbt.setAIN(2);
        hbt.createFrame();
    }
}