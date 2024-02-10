package lib.employee;

public abstract class Employee {

    static int $count = 0;

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    public abstract boolean generate(final long time);
    
    public abstract String toString();
}
