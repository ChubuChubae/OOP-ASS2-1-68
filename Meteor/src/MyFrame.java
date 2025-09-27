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
    private ImageIcon splash;

    public MyFrame() {
        setTitle("Meteor Display - Count: " + meteorCount);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        image = new ImageIcon("././Picture/images.jpg");
         splash = new ImageIcon("././Picture/splash.jpg");
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

        // **ขั้นตอนที่ 1: กำหนดขนาดใหม่สำหรับอุกกาบาต**
        int newMeteorWidth = 100; // กำหนดความกว้างใหม่
        int newMeteorHeight = 75; // กำหนดความสูงใหม่ (รักษาอัตราส่วนจาก 200x150 เป็น 100x75)

        for (int i = 0; i < this.meteorCount; i++) {
            int randx = new Random().nextInt(getWidth() - newMeteorWidth);
            int randy = new Random().nextInt(getHeight() - newMeteorHeight);

            // **ขั้นตอนที่ 2: ปรับขนาดรูปภาพ**
            Image scaledImage = image.getImage().getScaledInstance(
                    newMeteorWidth,
                    newMeteorHeight,
                    Image.SCALE_SMOOTH // ใช้ SCALE_SMOOTH เพื่อให้ภาพที่ย่อมีคุณภาพดี
            );
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel meteor = new JLabel();
            meteor.setLocation(randx, randy);
            meteor.setForeground(Color.white);

            // **ขั้นตอนที่ 3: ตั้งค่าขนาดของ JLabel ให้ตรงกับขนาดรูปภาพที่ย่อแล้ว**
            meteor.setSize(newMeteorWidth, newMeteorHeight);
            meteor.setIcon(scaledIcon);

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
        JLabel explosion = new JLabel();
        // Scale the splash image to fit a smaller size (e.g., 100x100)
        Image scaledSplash = splash.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        explosion.setIcon(new ImageIcon(scaledSplash));
        explosion.setSize(100, 100); // Set the size for the JLabel

        // Center the explosion relative to the collision point (x, y)
        explosion.setLocation(x - 50, y - 50); // Assuming x, y is the top-left of the meteor, adjust as needed for collision center

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