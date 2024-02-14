package lib.employee;

import lib.control.IBehaviour;

import java.awt.Graphics;

public abstract class Employee implements IBehaviour{
    
    protected int _x, _y;

    static int $count = 0;

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    public abstract boolean generate(final long time);
    
    public abstract String toString();

    public abstract void draw(Graphics g);
}
