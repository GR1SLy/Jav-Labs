package lib.employee;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Manager extends Employee {

    private static int $generateTime = 4, $generatePercent = 50, $count = 0, $lifeTime = 10;

    private double angle = 0.0;

    private int _centerX, _centerY, _radius = 100;

    private static Random _rand = new Random();

    private static Image _img = new ImageIcon("../lib/employee/images/manager.png").getImage();
    private static int _imageSize = 50;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGeneratePercent(final int generatePercent) { $generatePercent = generatePercent; }

    public static int getGeneratePercent() { return $generatePercent; }

    public static void setLifeTime(final int lifeTime) { $lifeTime = lifeTime; }

    public static int getLifeTime() { return $lifeTime; }

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    public static void decCount() { $count--; }

    public Manager(final int maxX, final int maxY, final int time, Integer id) {
        super();
        _rand = new Random();
        _centerX = _x = _rand.nextInt(_radius, maxX - _imageSize - _radius);
        _centerY = _y = _rand.nextInt(_radius, maxY - _imageSize - _radius);
        _birthTime = time;
        _id = id;
        $count++;
    }

    @Override
    public void move(boolean isRunning) {
        if (!isRunning) return;
        angle += 5 / (double)_radius;
        angle %= 2 * Math.PI;
        _x = _centerX + (int)(_radius * Math.cos(angle));
        _y = _centerY + (int)(_radius * Math.sin(angle));
    }

    @Override
    public String toString() {
        return "Manager: BirthTime: " + _birthTime + "; ID: " + _id;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(_img, _x, _y, _imageSize, _imageSize, null);
    }
    
}
