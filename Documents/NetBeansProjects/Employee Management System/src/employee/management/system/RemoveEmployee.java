package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.Vector;

public class RemoveEmployee extends JFrame implements ActionListener {

    JComboBox<String> comboEmpId;
    JButton delete, back;
    JLabel lblname, lblphone, lblemail;
    Vector<String> empIdList;

    RemoveEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel labelempId = new JLabel("Employee Id");
        labelempId.setBounds(50, 50, 100, 30);
        add(labelempId);

        empIdList = new Vector<>();
        comboEmpId = new JComboBox<>(empIdList);
        comboEmpId.setEditable(true);
        comboEmpId.setBounds(200, 50, 150, 30);
        add(comboEmpId);

        loadEmployeeIds();

        JTextField editor = (JTextField) comboEmpId.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String input = editor.getText();
                filterEmployeeIds(input);
            }
        });

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 100, 100, 30);
        add(labelname);

        lblname = new JLabel();
        lblname.setBounds(200, 100, 150, 30);
        add(lblname);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(50, 150, 100, 30);
        add(labelphone);

        lblphone = new JLabel();
        lblphone.setBounds(200, 150, 150, 30);
        add(lblphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 200, 100, 30);
        add(labelemail);

        lblemail = new JLabel();
        lblemail.setBounds(200, 200, 200, 30);
        add(lblemail);

        comboEmpId.addActionListener(e -> showEmployeeDetails((String) comboEmpId.getSelectedItem()));

        delete = new JButton("Delete");
        delete.setBounds(80, 300, 100, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 600, 400);
        add(image);

        setSize(1000, 400);
        setLocation(300, 150);
        setVisible(true);
    }

    private void loadEmployeeIds() {
        empIdList.clear();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select empId from employee");
            while (rs.next()) {
                empIdList.add(rs.getString("empId"));
            }
            updateComboBox(empIdList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateComboBox(Vector<String> list) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list);
        comboEmpId.setModel(model);
        comboEmpId.setSelectedItem(""); // start with empty
    }

    private void filterEmployeeIds(String input) {
        Vector<String> filtered = new Vector<>();
        for (String id : empIdList) {
            if (id.toLowerCase().contains(input.toLowerCase())) {
                filtered.add(id);
            }
        }
        updateComboBox(filtered);
        comboEmpId.setSelectedItem(input);
        comboEmpId.showPopup();
    }

    private void showEmployeeDetails(String empId) {
        if (empId == null || empId.trim().isEmpty()) return;

        try {
            Conn c = new Conn();
            String query = "select * from employee where empId = '" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                lblname.setText(rs.getString("name"));
                lblphone.setText(rs.getString("phone"));
                lblemail.setText(rs.getString("email"));
            } else {
                lblname.setText("");
                lblphone.setText("");
                lblemail.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            String empId = (String) comboEmpId.getSelectedItem();
            if (empId == null || empId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select an Employee ID.");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "delete from employee where empId = '" + empId + "'";
                int rows = c.s.executeUpdate(query);

                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Employee Information Deleted Successfully");
                    setVisible(false);
                    new Home();
                } else {
                    JOptionPane.showMessageDialog(null, "Employee ID not found");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}