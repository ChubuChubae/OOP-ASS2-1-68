
import javax.swing.JLabel;

public class Thread extends java.lang.Thread {
    JLabel label;
    float velocity;

    public Thread(JLabel label, float velocity) {
        this.label = label;
        this.velocity = velocity;
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
