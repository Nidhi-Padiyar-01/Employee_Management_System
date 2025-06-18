package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Splash extends JFrame implements ActionListener {

    JLabel heading;

    Splash() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set JFrame size to full screen
        setSize(screenWidth, screenHeight);
        setLocation(0, 0);

        heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        heading.setBounds(screenWidth / 4, 30, 1000, 50); // Center-ish
        heading.setFont(new Font("serif", Font.PLAIN, 50));
        heading.setForeground(Color.white);
        add(heading);

        // Load and scale the image to screen size
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/front.jpg"));
        Image i2 = i1.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, screenWidth, screenHeight);
        add(image);

        // Create button
        JButton clickhere = new JButton("CLICK HERE TO CONTINUE");
        clickhere.setBounds(screenWidth / 2 - 150, screenHeight - 150, 300, 70);
        clickhere.setBackground(Color.BLACK);
        clickhere.setForeground(Color.WHITE);
        clickhere.addActionListener(this);
        image.add(clickhere); // Add button to image

        setVisible(true);

        // Start blinking heading in a new thread
        
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login(); // or new Home();
    }

    public static void main(String args[]) {
        new Splash();
    }
}
