
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Amount extends JFrame {

    private JTextField amountMeteor;
    private JButton button;
    private MyFrame myFrame;

    Amount() {
        setTitle("Amount of Meteor");
        setSize(500, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout());
        amountMeteor = new JTextField();
        amountMeteor.setSize(300, 300);
        button = new JButton("Submit");
        button.setSize(100, 20);
        add(amountMeteor);
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
        String amountString = amountMeteor.getText();
        try {
            parsedAmount = Integer.parseInt(amountString);

            System.out.println("Parsed amount: " + parsedAmount); // debug
            if (myFrame != null) {
                myFrame.updateMeteorCount(parsedAmount);
                System.out.println("Sent to MyFrame successfully."); // debug
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please Enter A Valid Number.",
                    "Input Error!!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setMyFrame(MyFrame frame) {
        this.myFrame = frame;
    }
}
