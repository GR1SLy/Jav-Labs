package lib.employee;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

import java.util.Random;

import javax.swing.ImageIcon;

public class Manager extends Employee {
    
    private static int $generateTime = 0, $generatePercent = 0, $count = 0;

    private static Image _img = new ImageIcon("../lib/employee/images/manager.png").getImage();

    private static boolean _isImage = true;
    private static int _imageSize = 70;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGeneratePercent(final int generatePercent) { $generatePercent = generatePercent; }

    public static int getGeneratePercent() { return $generatePercent; }

    public static int getCount() { return $count; }
    
    public static void clear() { $count = 0; }

    public Manager() {
        super();
    }

    public Manager(final int maxX, final int maxY) {
        Random rnd = new Random();
        _x = rnd.nextInt(maxX);
        _y = rnd.nextInt(maxY);
    }

    @Override
    public boolean generate(final long time) { 
        if (time % $generateTime != 0) return false;
        float percent = 0;
        percent = (float)$count / (float)Developer.getCount() * 100;
        System.out.println("Percent of managers: " + percent);
        if ($generatePercent > percent) { Employee.$count++; $count++; return true; }
        else return false; 
    }

    @Override
    public String toString() { 
        return "Employee: Manager Generate time: " + $generateTime + " Generate percent: " + $generatePercent; 
    }

    @Override
    public void draw(Graphics g) {
        if (_isImage) {
            g.drawImage(_img, _x, _y, _imageSize, _imageSize, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(_x, _y, 50, 50);
        }
    }
    public void move() {}
}
