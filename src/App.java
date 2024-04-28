import lib.control.Habitat;
//cd "/Users/gr1sly/Files/Prog/Java/Lab/src/" && javac -d out App.java && cd out/ && java App && cd ..
public class App {
    public static void main(String[] args) throws Exception {
        Habitat hbt = new Habitat(1400, 800);
        hbt.setTimerAcceleration(1);
        hbt.setAIV(10);
        hbt.setAIN(2);
        hbt.createFrame();
    }
}