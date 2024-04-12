package lib.employee;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Developer extends Employee {

    private static int $generateTime = 2, $generateChance = 50, $count = 0, $lifeTime = 10, $moveTime = 3;

    private int _maxX, _maxY, _dx = 1, _dy = 1;

    private transient long _systemBirthTime = System.currentTimeMillis();

    private static Random $rand = new Random();

    private static Image $img = new ImageIcon("../lib/employee/images/developer.png").getImage();
    private static int $imageSize = 50;

    public static void setN(int N) { $moveTime = N; }

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGenerateChance(final int generateChance) { $generateChance = generateChance; }

    public static int getGenerateChance() { return $generateChance; }

    public static void setLifeTime(final int lifeTime) { $lifeTime = lifeTime; }

    public static int getLifeTime() { return $lifeTime; }

    public static int getCount() { return $count; }

    public static void setCount(int count) { $count = count; }

    public static void clear() { $count = 0; }

    public static void decCount() { $count--; Employee.decCount(); }

    public Developer(final int maxX, final int maxY, final int time, Integer id) {
        super();
        _x = $rand.nextInt(0, maxX - $imageSize);
        _y = $rand.nextInt(0, maxY - $imageSize);
        _birthTime = time;
        _id = id;
        _maxX = maxX;
        _maxY = maxY;
        newD();
        $count++;
    }

    @Override
    public void move(boolean isRunning) {
        if (!isRunning) return;
        _x += _dx;
        _y += _dy;
        if ((System.currentTimeMillis() - _systemBirthTime) / 1000 >= $moveTime) {
            newD();
            _systemBirthTime = System.currentTimeMillis();
        } else {
            if (_x < 0 || _x + $imageSize > _maxX) _dx *= -1;
            if (_y < 0 || _y + $imageSize > _maxY) _dy *= -1;
        }

    }

    private void newD() {
        int dx = $rand.nextInt(3) - 1;
        int dy = $rand.nextInt(3) - 1;
        while ((dx == 0 && dy == 0) || (dx == _dx && dy == _dy)) {
            dx = $rand.nextInt(3) - 1;
            dy = $rand.nextInt(3) - 1;
        }
        _dx = dx;
        _dy = dy;
    }

    @Override
    public String toString() {
        return "Developer: BirthTime: " + _birthTime + "; ID: " + _id;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage($img, _x, _y, $imageSize, $imageSize, null);
    }
    
}
