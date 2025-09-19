import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class Amount extends JFrame {
    JTextField amountmereor;
    JButton button;
    private MyFrame  myFrame;
    Amount()
    {
        setSize(500,100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout());
        amountmereor = new JTextField();
        amountmereor.setSize(300,300);
        button = new JButton("Submit");
        button.setSize(100,20);
        add(amountmereor);
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTextToInt();
            }
        });
    }

    public void convertTextToInt() {
        int parsedAmount;
        String amountString = amountmereor.getText();
        try {
            parsedAmount = Integer.parseInt(amountString);
            myFrame.updateMeteorCount(parsedAmount);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "กรุณาใส่ตัวเลขที่ถูกต้อง!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setMyFrame(MyFrame frame) {
        this.myFrame = frame;
    }
}

