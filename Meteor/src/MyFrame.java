import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MyFrame extends JFrame {

    private List<JLabel> meteorList;
    private List<MyThread> threadList;
    private int meteorCount = 0;
    private ImageIcon image;

    public MyFrame() {
        setTitle("Meteor Display - Count: " + meteorCount);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        image = new ImageIcon("././Picture/images.jpg");

        meteorList = new ArrayList<>();
        threadList = new ArrayList<>();

        createMeteor();
    }

    private void createMeteor() {
        // เคลียร์ Thread เก่า
        for (MyThread thread : threadList) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        threadList.clear();

        // เคลียร์ Meteor Jlabel เก่า
        for (JLabel meteor : meteorList) {
            if (meteor != null) {
                remove(meteor);
            }
        }
        meteorList.clear();

        int meteorWidth = 200;
        int meteorHeight = 150;

        for (int i = 0; i < this.meteorCount; i++) {
            int randx = new Random().nextInt(getWidth() - meteorWidth);
            int randy = new Random().nextInt(getHeight() - meteorHeight);

            JLabel meteor = new JLabel();
            meteor.setLocation(randx, randy);
            meteor.setForeground(Color.white);
            meteor.setSize(200, 150);
            meteor.setIcon(image);

            add(meteor);
            meteorList.add(meteor);

            MyThread thread = new MyThread(meteor, 8, this);
            threadList.add(thread);
            thread.start();
        }
        repaint();
    }

    public void updateMeteorCount(int newCount) {
        this.meteorCount = newCount;
        setTitle("Meteor Display - Count: " + meteorCount);
        createMeteor();
    }

    // เมธอดสำหรับตรวจสอบการชนกัน
    public boolean checkCollision(JLabel currentMeteor) {
        for (JLabel otherMeteor : meteorList) {
            if (otherMeteor != currentMeteor && otherMeteor.getParent() != null) {
                Rectangle rect1 = currentMeteor.getBounds();
                Rectangle rect2 = otherMeteor.getBounds();

                if (rect1.intersects(rect2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // เมธอดสำหรับลบอุกกาบาตที่ระเบิด
    public synchronized void removeMeteor(JLabel meteor) {
        //Swinguntility Invokelater
        if (meteorList.contains(meteor)) {
            remove(meteor);
            meteorList.remove(meteor);

            // หา thread ที่เกี่ยวข้องและหยุดมัน
            for (int i = 0; i < threadList.size(); i++) {
                if (threadList.get(i).getLabel() == meteor) {
                    threadList.get(i).interrupt();
                    threadList.remove(i);
                    break;
                }
            }

            meteorCount--;
            setTitle("Meteor Display - Count: " + meteorCount);
            repaint();
        }
    }

    // เมธอดสำหรับสร้างเอฟเฟกต์การระเบิด
    public void createExplosion(int x, int y) {
        JLabel explosion = new JLabel("💥");
        explosion.setFont(new Font("Arial", Font.BOLD, 50));
        explosion.setForeground(Color.ORANGE);
        explosion.setBounds(x, y, 100, 100);
        add(explosion);
        repaint();

        // ลบเอฟเฟกต์หลังจาก 500ms
        Timer timer = new Timer(500, e -> {
            remove(explosion);
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
}