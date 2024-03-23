import lib.control.Habitat;

public class App {
    public static void main(String[] args) throws Exception {
        Habitat hbt = new Habitat(150, 150, 1400, 800);
        hbt.setTimerAcceleration(2);
        hbt.setAIV(10);
        hbt.setAIN(2);
        hbt.createFrame();
    }
}
