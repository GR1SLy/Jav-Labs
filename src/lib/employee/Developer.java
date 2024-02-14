package lib.employee;

import java.util.Random;

public class Developer extends Employee {
    
    private static int $generateTime = 0, $generateChance = 0, $count = 0;

    public static void setGenerateTime(final int generateTime) { $generateTime = generateTime; }

    public static int getGenerateTime() { return $generateTime; }

    public static void setGenerateChance(final int generateChance) { $generateChance = generateChance; }

    public static int getGenerateChance() { return $generateChance; }

    public static int getCount() { return $count; }

    public static void clear() { $count = 0; }

    @Override
    public boolean generate(final long time) {
        if (time % $generateTime != 0) return false;
        Random rnd = new Random();
        int chance = rnd.nextInt(100) + 1;
        System.out.println("Random: " + chance);
        if (chance < $generateChance) { Employee.$count++; $count++; return true; }
        else return false;
    }

    @Override
    public String toString() { 
        return "Employee: Developer Generate time: " + $generateTime + " Generate chance: " + $generateChance;
    }

    public void move() {}
}
