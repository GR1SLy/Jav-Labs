package lib.employee;

import lib.control.IBehaviour;

import java.awt.Graphics;

public abstract class Employee implements IBehaviour{
    
    protected int _x, _y, _id;

    protected long _birthTime;

    static int $count = 0;

    public Employee() {
        _x = _y = 0;
        _birthTime = 0;
        _id = 0;
    }

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    public int getID() { return _id; }

    public abstract boolean generate(final long time);

    public abstract boolean terminate(final long time);
    
    public abstract String toString();

    public abstract void draw(Graphics g);
}
