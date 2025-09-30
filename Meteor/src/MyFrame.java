import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.swing.*;

public class MyFrame extends JFrame {

    private List<JLabel> meteorList;
    private List<MyThread> threadList;
    private int meteorCount = 0;
    private ImageIcon image;
    private ImageIcon splash;
    private List<ImageIcon> images; // รายการภาพอุกกาบาตทั้งหมด

    public MyFrame() {
        setTitle("Meteor Display - Count: " + meteorCount);
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        image = new ImageIcon("././Picture/images.jpg");
        splash = new ImageIcon("././Picture/splash.jpg");

        // โหลดภาพจากโฟลเดอร์
        images = loadImagesFromFolder("././Picture/meteor/");

        meteorList = new ArrayList<>();
        threadList = new ArrayList<>();

        createMeteor();
    }

    // เมธอดสำหรับโหลดภาพทั้งหมดจากโฟลเดอร์
    private List<ImageIcon> loadImagesFromFolder(String folderPath) {
        List<ImageIcon> imageList = new ArrayList<>();
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return null;
        }
        File[] files = folder.listFiles((dir, name) -> {
            String lowerName = name.toLowerCase();
            return lowerName.endsWith(".jpg")
                    || lowerName.endsWith(".jpeg")
                    || lowerName.endsWith(".png")
                    || lowerName.endsWith(".gif");
        });
        if (files != null && files.length > 0) {
            for (File file : files) {
                ImageIcon img = new ImageIcon(file.getAbsolutePath());
                imageList.add(img);
            }
        }
        return imageList.isEmpty() ? null : imageList;
    }

    // เมธอดสำหรับสุ่มเลือกภาพ
    private ImageIcon getRandomImage(List<ImageIcon> imageList) {
        if (imageList == null || imageList.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(imageList.size());
        return imageList.get(randomIndex);
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

        // กำหนดขนาดใหม่สำหรับอุกกาบาต
        int newMeteorWidth = 100;
        int newMeteorHeight = 75;

        for (int i = 0; i < this.meteorCount; i++) {
            int randx = new Random().nextInt(getWidth() - newMeteorWidth);
            int randy = new Random().nextInt(getHeight() - newMeteorHeight);

            // เลือกภาพแบบสุ่ม (ถ้าโหลดภาพสำเร็จ จะใช้ภาพจากโฟลเดอร์ ถ้าไม่ใช้ภาพเดิม)
            ImageIcon currentImage = (images != null && !images.isEmpty())
                    ? getRandomImage(images)
                    : image;

            // ปรับขนาดรูปภาพ
            Image scaledImage = currentImage.getImage().getScaledInstance(
                    newMeteorWidth,
                    newMeteorHeight,
                    Image.SCALE_SMOOTH
            );
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel meteor = new JLabel();
            meteor.setLocation(randx, randy);
            meteor.setForeground(Color.white);

            // ตั้งค่าขนาดของ JLabel ให้ตรงกับขนาดรูปภาพที่ย่อแล้ว
            meteor.setSize(newMeteorWidth, newMeteorHeight);
            meteor.setIcon(scaledIcon);

            add(meteor);
            meteorList.add(meteor);
            Random rand = new Random();

            MyThread thread = new MyThread(meteor,rand.nextInt(6)+5 , this);
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
        Image scaledSplash = splash.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        explosion.setIcon(new ImageIcon(scaledSplash));
        explosion.setSize(100, 100);

        explosion.setLocation(x - 50, y - 50);

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