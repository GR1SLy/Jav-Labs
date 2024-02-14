package lib.employee;

import lib.control.IBehaviour;

public abstract class Employee implements IBehaviour{

    static int $count = 0;

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    public abstract boolean generate(final long time);
    
    public abstract String toString();
}
