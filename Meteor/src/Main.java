
public class Main {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        Amount amount = new Amount();

        amount.setMyFrame(frame);

        frame.setVisible(true);
        amount.setVisible(true);
    }
}
