package lib.employee;

import java.util.Random;

import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

public class Developer extends Employee {
    
    private static int $generateTime = 0, $generateChance = 0, $count = 0;

    private static Image _img = new ImageIcon("../lib/employee/images/developer.png").getImage();

    private static boolean _isImage = true;
    private static int _imageSize = 50;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGenerateChance(final int generateChance) { $generateChance = generateChance; }

    public static int getGenerateChance() { return $generateChance; }

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    public Developer() {
        super();
    }

    public Developer(final int maxX, final int maxY) {
        Random rnd = new Random();
        _x = rnd.nextInt(0, maxX - _imageSize);
        _y = rnd.nextInt(0, maxY - _imageSize);
    }

    @Override
    public boolean generate(final long time) {
        if (time % $generateTime != 0) return false;
        Random rnd = new Random();
        int chance = rnd.nextInt(100) + 1;
        System.out.println("Random: " + chance);
        if (chance < $generateChance) { Employee.$count++; $count++; return true; }
        else return false;
    }

    @Override
    public String toString() { 
        return "Employee: Developer Generate time: " + $generateTime + " Generate chance: " + $generateChance;
    }

    @Override
    public void draw(Graphics g) {
        if (_isImage) {
            g.drawImage(_img, _x, _y, _imageSize, _imageSize, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillOval(_x, _y, 30, 30);
        }
    }

    public void move() {}
}
