import javax.swing.JLabel;

public class MyThread extends Thread {

    private JLabel label;
    private int velocity;
    private int dx = 5; // ความเร็วในแนวแกน X
    private int dy = 5; // ความเร็วในแนวแกน Y
    private MyFrame parent;
    private boolean exploded = false;

    public MyThread(JLabel label, int velocity, MyFrame parent) {
        this.label = label;
        this.velocity = velocity;
        this.parent = parent;
    }

    public JLabel getLabel() {
        return label;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted() && !exploded) {
                // ตรวจสอบการชนกันก่อนเคลื่อนที่
                if (parent.checkCollision(label)) {
                    exploded = true;

                    // สร้างเอฟเฟกต์การระเบิด
                    int explosionX = label.getX() + label.getWidth() / 2;
                    int explosionY = label.getY() + label.getHeight() / 2;
                    parent.createExplosion(explosionX, explosionY);

                    // ลบอุกกาบาตที่ระเบิด
                    parent.removeMeteor(label);
                    break;
                }

                int newX = label.getX() + dx;
                int newY = label.getY() + dy;
                int screenWidth = label.getParent().getWidth();
                int screenHeight = label.getParent().getHeight();

                // ตรวจสอบการชนขอบหน้าจอ
                if (newY <= 0) {
                    newY = 0;
                    dy *= -1;
                    this.velocity = (int) (this.velocity * 0.8);
                    if (this.velocity < 1) {
                        this.velocity = 1;
                    }
                } else if (newY + label.getHeight() >= screenHeight) {
                    newY = screenHeight - label.getHeight();
                    dy *= -1;
                    this.velocity = (int) (this.velocity * 0.8);
                    if (this.velocity < 1) {
                        this.velocity = 1;
                    }
                }

                if (newX <= 0) {
                    newX = 0;
                    dx *= -1;
                    this.velocity = (int) (this.velocity * 0.8);
                    if (this.velocity < 1) {
                        this.velocity = 1;
                    }
                } else if (newX + label.getWidth() >= screenWidth) {
                    newX = screenWidth - label.getWidth();
                    dx *= -1;
                    this.velocity = (int) (this.velocity * 0.8);
                    if (this.velocity < 1) {
                        this.velocity = 1;
                    }
                }

                label.setLocation(newX, newY);

                Thread.sleep(5 * velocity);
            }
        } catch (InterruptedException e) {
            // Thread ถูกขัดจังหวะ ให้หยุดทำงาน
            exploded = true;
        }
    }
}