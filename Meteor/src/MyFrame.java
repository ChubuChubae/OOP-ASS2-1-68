import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class MyFrame extends JFrame {
    JLabel[] meteor;
    int meteorCount = 5;
    public MyFrame() {

        MyThread []thread = new MyThread[20];
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        ImageIcon image = new ImageIcon("././Picture/images.jpg");
        meteor = new JLabel[this.meteorCount];
        for(int i = 0 ; i < meteor.length;i++)
        {   int randx = new Random().nextInt(750);
            int randy = new Random().nextInt(750);
            meteor[i] = new JLabel();
            meteor[i].setLocation(randx,randy);
            meteor[i].setForeground(Color.white);
            meteor[i].setSize(200,150);
            thread[i] = new MyThread(meteor[i],5);
            thread[i].start();
            meteor[i].setIcon(image);
            add(meteor[i]);
            repaint();
        }

}
    public void updateMeteorCount(int newCount) {
        this.meteorCount = newCount;
        setTitle("Meteor Display - Count: " + meteorCount);
        // createMeteors();

    }

}
