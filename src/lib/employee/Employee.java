package lib.employee;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Employee implements IBehaviour, Serializable {

    private static final long serialVersionUID = 6473L;
    
    protected int _x, _y, _id;

    protected int _birthTime;

    private static int $count = 0;

    public Employee() {
        _x = _y = 0;
        _birthTime = 0;
        _id = 0;
        $count++;
    }

    public static int getCount() { return $count; }

    public static void setCount(int count) { $count = count; }

    public static void clear() { $count = 0; }

    public static void decCount() { $count--; }

    public int getID() { return _id; }

    public int getBirthTime() { return _birthTime; }

    public int getX() { return _x; }

    public int getY() { return _y; }
    
    public abstract String toString();

    public abstract void draw(Graphics g);
}
