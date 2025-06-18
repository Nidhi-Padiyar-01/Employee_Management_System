package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    JButton view, add, update, remove;

    Home() {
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set layout and make undecorated full screen
        setLayout(null);
        setUndecorated(true); // Optional: removes title bar

        // Load and scale background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, screenWidth, screenHeight);
        setContentPane(image);
        image.setLayout(null);

        // Heading
        JLabel heading = new JLabel("Employee Management System");
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        heading.setForeground(Color.black);
        heading.setBounds((screenWidth - 500) / 2, 40, 500, 40);
        image.add(heading);

        // Buttons
        add = new JButton("Add Employee");
        add.setBounds((screenWidth / 2) - 160, 120, 180, 50);
        add.addActionListener(this);
        image.add(add);

        view = new JButton("View Employees");
        view.setBounds((screenWidth / 2) + 20, 120, 180, 50);
        view.addActionListener(this);
        image.add(view);

        update = new JButton("Update Employee");
        update.setBounds((screenWidth / 2) - 160, 190, 180, 50);
        update.addActionListener(this);
        image.add(update);

        remove = new JButton("Remove Employee");
        remove.setBounds((screenWidth / 2) + 20, 190, 180, 50);
        remove.addActionListener(this);
        image.add(remove);

        // Final settings
        setSize(screenWidth, screenHeight);
        setLocation(0, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        if (ae.getSource() == add) {
            new AddEmployee();
        } else if (ae.getSource() == view || ae.getSource() == update) {
            new ViewEmployee(); // both view and update go to ViewEmployee
        } else {
            new RemoveEmployee();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
