
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class MyFrame extends JFrame {

    private JLabel[] meteor;
    private int meteorCount = 0;
    private ImageIcon image;
    private MyThread[] thread;

    public MyFrame() {
        setTitle("Meteor Display - Count: " + meteorCount);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        image = new ImageIcon("././Picture/images.jpg");

        createMeteor();
    }

    private void createMeteor() {

        // เคลียร์ Thread เก่า
        if (thread != null) {
            for (int i = 0; i < thread.length; i++) {
                if (thread[i] != null) {
                    thread[i].interrupt();
                    thread[i] = null;
                }
            }
        }

        // เคลียร์ Meteor Jlabel เก่า
        if (meteor != null) {
            for (int i = 0; i < meteor.length; i++) {
                if (meteor[i] != null) {
                    remove(meteor[i]);
                    meteor[i] = null;
                }
            }
        }

        meteor = new JLabel[this.meteorCount];
        thread = new MyThread[this.meteorCount];
        for (int i = 0; i < meteor.length; i++) {
            int randx = new Random().nextInt(750);
            int randy = new Random().nextInt(750);
            meteor[i] = new JLabel();
            meteor[i].setLocation(randx, randy);
            meteor[i].setForeground(Color.white);
            meteor[i].setSize(200, 150);

            meteor[i].setIcon(image);
            add(meteor[i]);

            thread[i] = new MyThread(meteor[i], 5);
            thread[i].start();
        }
        repaint();
    }

    public void updateMeteorCount(int newCount) {
        this.meteorCount = newCount;
        setTitle("Meteor Display - Count: " + meteorCount);
        createMeteor();

    }

}
