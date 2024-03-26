package lib.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import lib.employee.Developer;
import lib.employee.Manager;

public class ConfigOperator {

    private static String $direction = "../cfg.cfg";

    static void setDirection(String dir) { $direction = dir; }

    class boolPair {
        public boolean showInfo, showTimer;
        public boolPair(boolean showInfo, boolean showTimer) {
            super();
            this.showInfo = showInfo;
            this.showTimer = showTimer;
        }
    }

    public ConfigOperator() {
        super();
    }

    public ConfigOperator(String dir) {
        super();
        $direction = dir;
    }

    void writeConfig(boolean showInfo, boolean showTimer) {
        try {
            FileWriter writer = new FileWriter($direction);
            String wString = Developer.getGenerateTime() + "\n" + Developer.getLifeTime() + "\n" + Developer.getGenerateChance() + "\n" +
                             Manager.getGenerateTime() + "\n" + Manager.getLifeTime() + "\n" + Manager.getGeneratePercent() + "\n";
            if (showInfo) wString += "1\n"; else wString += "0\n";
            if (showTimer) wString += "1\n"; else wString += "0\n";
            writer.write(wString);
            writer.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    boolPair readConfig() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader($direction));
            Developer.setGenerateTime(Integer.parseInt(reader.readLine()));
            Developer.setLifeTime(Integer.parseInt(reader.readLine()));
            Developer.setGenerateChance(Integer.parseInt(reader.readLine()));
            Manager.setGenerateTime(Integer.parseInt(reader.readLine()));
            Manager.setLifeTime(Integer.parseInt(reader.readLine()));
            Manager.setGeneratePercent(Integer.parseInt(reader.readLine()));
            boolean showInfo = (Integer.parseInt(reader.readLine()) == 1);
            boolean showTimer = (Integer.parseInt(reader.readLine()) == 1);
            reader.close();
            return new boolPair(showInfo, showTimer);
        } catch (Exception e) { 
            System.err.println("No such file: " + $direction + "\nNew file will be created");
         }
        return new boolPair(false, true);
    }
}

/*
 * CFG:
 * dev time
 * dev lifeTime
 * dev chance
 * man time
 * man lifeTime
 * man percent
 * show info
 * hide timer
 */
