import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MyFrame extends JFrame {
    JLabel[] meteor;

    public MyFrame() {
        MyThread []thread = new MyThread[20];
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        ImageIcon image = new ImageIcon("././Picture/images.jpg");
        meteor = new JLabel[2];
        meteor[0] = new JLabel("123");
        meteor[0].setForeground(Color.white);
        meteor[0].setLocation(100,100);
        meteor[0].setSize(200,200);
        thread[0] = new MyThread(meteor[0],5);
        thread[0].start();
        meteor[0].setIcon(image);
        add(meteor[0]);


}
}
