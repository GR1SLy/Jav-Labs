package lib.employee;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

import java.util.Random;

import javax.swing.ImageIcon;

public class Manager extends Employee {
    
    private static int $generateTime = 0, $generatePercent = 0, $count = 0, $lifeTime;

    private static Image _img = new ImageIcon("../lib/employee/images/manager.png").getImage();

    private static boolean _isImage = true;
    private static int _imageSize = 70;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGeneratePercent(final int generatePercent) { $generatePercent = generatePercent; }

    public static int getGeneratePercent() { return $generatePercent; }

    public static void setLifeTime(final int lifeTime) { $lifeTime = lifeTime * 1000; }

    public static int getLifeTime() { return $lifeTime; }

    public static int getCount() { return $count; }
    
    public static void clear() { $count = 0; }

    public Manager() {
        super();
    }

    public Manager(final int maxX, final int maxY, final long time) {
        Random rnd = new Random();
        _x = rnd.nextInt(0, maxX - _imageSize);
        _y = rnd.nextInt(0, maxY - _imageSize);
        _birthTime = time;

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
    public boolean terminate(final long time) {
        boolean result = false;
        if (time - _birthTime >= $lifeTime) { $count--; Employee.$count--; result = true; }
        else result = false;
        if (result) System.out.println("Time: " + time + " BirthTime: " + _birthTime + " result: " + result);
        return result;
    }

    @Override
    public String toString() { 
        return "Manager Generate time: " + $generateTime + " Generate percent: " + $generatePercent + " LifeTime: " + $lifeTime + " BirthTime: " + _birthTime; 
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
