package lib.employee;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Manager extends Employee {

    private static int $generateTime = 4, $generatePercent = 50, $count = 0, $lifeTime = 10;

    private double _angle = 0.0;

    private int _centerX, _centerY, _radius = 100;

    private static Random $rand = new Random();

    private static Image $img = new ImageIcon("../lib/employee/images/manager.png").getImage();
    private static int $imageSize = 50;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGeneratePercent(final int generatePercent) { $generatePercent = generatePercent; }

    public static int getGeneratePercent() { return $generatePercent; }

    public static void setLifeTime(final int lifeTime) { $lifeTime = lifeTime; }

    public static int getLifeTime() { return $lifeTime; }

    public static int getCount() { return $count; }

    public static void setCount(int count) { $count = count; }

    public static void clear() { $count = 0; }

    public static void decCount() { $count--; Employee.decCount(); }

    public Manager(final int maxX, final int maxY, final int time, Integer id) {
        super();
        while ((maxX - $imageSize - _radius) < _radius || (maxY - $imageSize - _radius) < _radius) _radius /= 2;
        _centerX = _x = $rand.nextInt(_radius, maxX - $imageSize - _radius);
        _centerY = _y = $rand.nextInt(_radius, maxY - $imageSize - _radius);
        _birthTime = time;
        _id = id;
        $count++;
    }

    @Override
    public void move(boolean isRunning) {
        if (!isRunning) return;
        _angle += 5 / (double)_radius;
        _angle %= 2 * Math.PI;
        _x = _centerX + (int)(_radius * Math.cos(_angle));
        _y = _centerY + (int)(_radius * Math.sin(_angle));
    }

    @Override
    public String toString() {
        return "Manager: BirthTime: " + _birthTime + "; ID: " + _id;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage($img, _x, _y, $imageSize, $imageSize, null);
    }
    
}
