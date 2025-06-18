package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import java.util.Vector;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    JComboBox<String> comboEmpId;
    JButton search, print, update, back;
    Vector<String> empIdList;

    ViewEmployee() {

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximized window
        setTitle("View Employee");

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Top Panel for controls
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new Dimension(1200, 100));

        JLabel searchlbl = new JLabel("Search by Employee Id");
        searchlbl.setBounds(20, 30, 150, 20);
        topPanel.add(searchlbl);

        empIdList = new Vector<>();
        comboEmpId = new JComboBox<>(empIdList);
        comboEmpId.setEditable(true);
        comboEmpId.setBounds(180, 30, 150, 20);
        topPanel.add(comboEmpId);

        // Load employee IDs from DB
        loadEmployeeIds();

        // Add filtering as user types
        JTextField editor = (JTextField) comboEmpId.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String input = editor.getText();
                filterEmployeeIds(input);
            }
        });

        search = new JButton("Search");
        search.setBounds(350, 30, 100, 25);
        search.addActionListener(this);
        topPanel.add(search);

        print = new JButton("Print");
        print.setBounds(460, 30, 100, 25);
        print.addActionListener(this);
        topPanel.add(print);

        update = new JButton("Update");
        update.setBounds(570, 30, 100, 25);
        update.addActionListener(this);
        topPanel.add(update);

        back = new JButton("Back");
        back.setBounds(680, 30, 100, 25);
        back.addActionListener(this);
        topPanel.add(back);

        add(topPanel, BorderLayout.NORTH);

        // Table Panel
        table = new JTable();
        loadAllEmployees();

        JScrollPane jsp = new JScrollPane(table);
        add(jsp, BorderLayout.CENTER);

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
        comboEmpId.setSelectedItem(""); // reset to empty
    }

    private void filterEmployeeIds(String input) {
        Vector<String> filtered = new Vector<>();
        for (String id : empIdList) {
            if (id.toLowerCase().contains(input.toLowerCase())) {
                filtered.add(id);
            }
        }
        updateComboBox(filtered);
        comboEmpId.setSelectedItem(input); // retain typed text
        comboEmpId.showPopup(); // show dropdown
    }

    private void loadAllEmployees() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from employee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        String selectedEmpId = (String) comboEmpId.getSelectedItem();

        if (ae.getSource() == search) {
            if (selectedEmpId == null || selectedEmpId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter or select an Employee ID.");
                return;
            }

            String query = "select * from employee where empId = '" + selectedEmpId + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == update) {
            if (selectedEmpId == null || selectedEmpId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select an Employee ID to update.");
                return;
            }
            setVisible(false);
            new UpdateEmployee(selectedEmpId);

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
