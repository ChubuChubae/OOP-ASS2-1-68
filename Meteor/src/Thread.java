
import javax.swing.JLabel;

public class Thread extends java.lang.Thread {
    JLabel label;

    public Thread(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
