import lib.control.Habitat;

public class App {
    public static void main(String[] args) throws Exception {
        Habitat hbt = new Habitat(150, 150, 1400, 800);
        hbt.setTimerAcceleration(10);
        hbt.createFrame();
    }
}
