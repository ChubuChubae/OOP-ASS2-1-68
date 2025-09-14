
import javax.swing.JLabel;

public class MyThread extends Thread {
    JLabel label;
    int velocity;
    int direction = 1;

    public MyThread(JLabel label, int velocity) {
        this.label = label;
        this.velocity = velocity;
    }

    @Override
    public void run() {
        while (true) {
            int newY = label.getY() + (5 * direction);
            int newX = label.getX() + (5*direction);
//            // ถ้าชนขอบล่างหรือบน ให้เปลี่ยนทิศทาง
            if (newY >= 250 || newY <= 0) {
                direction *= -1;
            }
           else if (newX >= 250 || newX <= 0) {
                direction *= -1;
            }

            label.setLocation(newX, newY);

            try {
                Thread.sleep(5*velocity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
