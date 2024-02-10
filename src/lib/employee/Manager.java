package lib.employee;

import lib.control.IBehaviour;

public class Manager extends Employee implements IBehaviour {
    private static int $generateTime = 0, $generatePercent = 0, $count = 0;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGeneratePercent(final int generatePercent) { $generatePercent = generatePercent; }

    public static int getGeneratePercent() { return $generatePercent; }

    public static int getCount() { return $count; }
    
    public static void clear() { $count = 0; }

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

    public void move() {}
}
