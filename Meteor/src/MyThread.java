
import javax.swing.JLabel;

public class MyThread extends Thread {
    JLabel label;
    int velocity;
    int direction = 1;
    int dx = 5; // ความเร็วในแนวแกน X
    int dy = 5; // ความเร็วในแนวแกน Y

    public MyThread(JLabel label, int velocity) {
        this.label = label;
        this.velocity = velocity;
    }

    @Override
    public void run() {
        while (true) {
            int newX = label.getX() + dx;
            int newY = label.getY() + dy;
//            // ถ้าชนขอบล่างหรือบน ให้เปลี่ยนทิศทาง
            if (newY <= 0 || newY + label.getHeight() >= label.getParent().getHeight()) {
                dy *= -1;
            }
           else if (newX <= 0 || newX + label.getWidth() >= label.getParent().getWidth()) {
                dx *= -1;
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
