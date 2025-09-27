import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem {
    DefaultTableModel bookModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Publisher", "Publication Date", "Price", "Quantity"}, 0);
    DefaultTableModel issueModel = new DefaultTableModel(new String[]{"Book ID", "Reader ID", "Issue Date"}, 0);
    DefaultTableModel staffModel = new DefaultTableModel(new String[]{"Staff ID", "Name", "Position"}, 0);
    DefaultTableModel readerModel = new DefaultTableModel(new String[]{"Reader ID", "Name"}, 0);
    DefaultTableModel returnModel = new DefaultTableModel(new String[]{"Book Name", "Reader ID", "Return Date"}, 0);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystem().showLoginWindow());
    }

    void showLoginWindow() {
        JFrame loginFrame = new JFrame("Library Login");
        loginFrame.setSize(1000, 1000);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);
        loginFrame.getContentPane().setBackground(Color.WHITE);

    JComponent image1=new JComponent() {
        Image im=new ImageIcon("Screenshot 2025-05-29 115634.png").getImage();
        	
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(im,0,0, getWidth(),getHeight(),this);
        }
        };
 image1.setBounds(500,0,500,1000);
 loginFrame.add(image1);
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.BLACK);
        userLabel.setBounds(20,30,100,70);
        
        JTextField userField = new JTextField();
        userField.setBounds(140,30,200,50);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);
        passLabel.setBounds(20,150,100,70);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(140,150,200,50);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(Color.BLACK);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBounds(150,260,150,50);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            if ("admin".equals(username) && "admin".equals(password)) {
                loginFrame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(userLabel); loginFrame.add(userField);
        loginFrame.add(passLabel); loginFrame.add(passField);
        loginFrame.add(new JLabel()); loginFrame.add(loginBtn);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    void showMainMenu() {
        JFrame mainFrame = new JFrame("Library Management System - Main Menu");
        mainFrame.setSize(400, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.getContentPane().setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        mainFrame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        String[] btnNames = {"Books", "Issue Info", "Staff", "Readers", "Return Book", "Statistics", "Logout"};
        JButton[] buttons = new JButton[7];

        for (int i = 0; i < 7; i++) {
            buttons[i] = new JButton(btnNames[i]);
            buttons[i].setBackground(Color.BLACK);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 16));
            buttons[i].setFocusPainted(false);
            buttonPanel.add(buttons[i]);
        }

        buttons[0].addActionListener(e -> showBooksWindow());
        buttons[1].addActionListener(e -> showIssueWindow());
        buttons[2].addActionListener(e -> showStaffWindow());
        buttons[3].addActionListener(e -> showReadersWindow());
        buttons[4].addActionListener(e -> showReturnWindow());
        buttons[5].addActionListener(e -> showStatisticsWindow());
        buttons[6].addActionListener(e -> System.exit(0));

        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    void showBooksWindow() {
        JFrame frame = createDataEntryFrame("Books Data Entry", 950, 500);
        JTable table = new JTable(bookModel);
        styleTable(table);

        JPanel panel = createFormPanel(new String[]{"Book ID", "Title", "Author", "Publisher", "Publication Date", "Price", "Quantity"});
        JTextField[] fields = getFieldsFromPanel(panel);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);
        panel.add(new JLabel()); panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Object[] row = {
                        Integer.parseInt(fields[0].getText().trim()), fields[1].getText().trim(),
                        fields[2].getText().trim(), fields[3].getText().trim(),
                        fields[4].getText().trim(), Double.parseDouble(fields[5].getText().trim()),
                        Integer.parseInt(fields[6].getText().trim())
                };
                bookModel.addRow(row);
                for (JTextField field : fields) field.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    void showIssueWindow() {
        JFrame frame = createDataEntryFrame("Issue Info Data Entry", 700, 400);
        JTable table = new JTable(issueModel);
        styleTable(table);

        JPanel panel = createFormPanel(new String[]{"Book ID", "Reader ID", "Issue Date"});
        JTextField[] fields = getFieldsFromPanel(panel);

        JButton addBtn = new JButton("Add Issue");
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);
        panel.add(new JLabel()); panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Object[] row = {
                        Integer.parseInt(fields[0].getText().trim()),
                        Integer.parseInt(fields[1].getText().trim()),
                        fields[2].getText().trim()
                };
                issueModel.addRow(row);
                for (JTextField field : fields) field.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    void showStaffWindow() {
        JFrame frame = createDataEntryFrame("Staff Data Entry", 700, 400);
        JTable table = new JTable(staffModel);
        styleTable(table);

        JPanel panel = createFormPanel(new String[]{"Staff ID", "Name", "Position"});
        JTextField[] fields = getFieldsFromPanel(panel);

        JButton addBtn = new JButton("Add Staff");
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);
        panel.add(new JLabel()); panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Object[] row = {
                        Integer.parseInt(fields[0].getText().trim()),
                        fields[1].getText().trim(),
                        fields[2].getText().trim()
                };
                staffModel.addRow(row);
                for (JTextField field : fields) field.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    void showReadersWindow() {
        JFrame frame = createDataEntryFrame("Readers Data Entry", 600, 400);
        JTable table = new JTable(readerModel);
        styleTable(table);

        JPanel panel = createFormPanel(new String[]{"Reader ID", "Name"});
        JTextField[] fields = getFieldsFromPanel(panel);

        JButton addBtn = new JButton("Add Reader");
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);
        panel.add(new JLabel()); panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Object[] row = {
                        Integer.parseInt(fields[0].getText().trim()),
                        fields[1].getText().trim()
                };
                readerModel.addRow(row);
                for (JTextField field : fields) field.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    void showReturnWindow() {
        JFrame frame = createDataEntryFrame("Return Book Entry", 700, 400);
        JTable table = new JTable(returnModel);
        styleTable(table);

        JPanel panel = createFormPanel(new String[]{"Book Name", "Reader ID", "Return Date"});
        JTextField[] fields = getFieldsFromPanel(panel);

        JButton addBtn = new JButton("Return Book");
        addBtn.setBackground(Color.BLACK);
        addBtn.setForeground(Color.WHITE);
        panel.add(new JLabel()); panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Object[] row = {
                        fields[0].getText().trim(),
                        Integer.parseInt(fields[1].getText().trim()),
                        fields[2].getText().trim()
                };
                returnModel.addRow(row);
                for (JTextField field : fields) field.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    void showStatisticsWindow() {
        JFrame frame = new JFrame("Statistics");
        frame.setSize(1000, 600);
        frame.setLayout(new GridLayout(5, 1));
        frame.getContentPane().setBackground(Color.WHITE);

        JTable[] tables = {
                new JTable(bookModel),
                new JTable(issueModel),
                new JTable(staffModel),
                new JTable(readerModel),
                new JTable(returnModel)
        };

        for (JTable table : tables) {
            styleTable(table);
            frame.add(new JScrollPane(table));
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    JFrame createDataEntryFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);
        return frame;
    }

    JPanel createFormPanel(String[] labels) {
        JPanel panel = new JPanel(new GridLayout(labels.length + 1, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        for (String label : labels) {
            panel.add(new JLabel(label + ":"));
            panel.add(new JTextField());
        }
        return panel;
    }

    JTextField[] getFieldsFromPanel(JPanel panel) {
        List<JTextField> fields = new ArrayList<>();
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                fields.add((JTextField) comp);
            }
        }
        return fields.toArray(new JTextField[0]);
    }

    void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJavaJDBCExample {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        String DB_URL = "jdbc:oracle:thin:@//10.11.0.43:1521/XE";
        String USER = "FA24CS083";
        String PASS = "oracle";

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(true); 
            stmt = conn.createStatement();
            System.out.println("Connected to the Oracle DB!");

            
            String[] sequences = { "seq_book_id", "seq_reader_id", "seq_staff_id", "seq_issue_id", "seq_return_id" };
            for (String seq : sequences) {
                String dropSeqSQL = "BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE " + seq + "'; EXCEPTION WHEN OTHERS THEN NULL; END;";
                stmt.execute(dropSeqSQL);
                System.out.println("Dropped sequence (if existed): " + seq);
            }

            
            String[] tables = { "ReturnBook", "IssueInfo", "Staff", "Readers", "Books" };
            for (String tbl : tables) {
                String dropTableSQL = "BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + tbl + " CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;";
                stmt.execute(dropTableSQL);
                System.out.println("Dropped table (if existed): " + tbl);
            }

            
            stmt.executeUpdate("CREATE SEQUENCE seq_book_id START WITH 1001 INCREMENT BY 1");
            stmt.executeUpdate("CREATE SEQUENCE seq_reader_id START WITH 2001 INCREMENT BY 1");
            stmt.executeUpdate("CREATE SEQUENCE seq_staff_id START WITH 3001 INCREMENT BY 1");
            stmt.executeUpdate("CREATE SEQUENCE seq_issue_id START WITH 4001 INCREMENT BY 1");
            stmt.executeUpdate("CREATE SEQUENCE seq_return_id START WITH 5001 INCREMENT BY 1");
            System.out.println("Sequences created successfully.");

            stmt.executeUpdate("CREATE TABLE Books (" +
                    "BookID NUMBER PRIMARY KEY, Title VARCHAR2(100), Author VARCHAR2(100), " +
                    "Publisher VARCHAR2(100), PublishedDate DATE, Price NUMBER(7,2), Quantity NUMBER)");

            stmt.executeUpdate("CREATE TABLE Readers (" +
                    "ReaderID NUMBER PRIMARY KEY, Name VARCHAR2(100))");

            stmt.executeUpdate("CREATE TABLE Staff (" +
                    "StaffID NUMBER PRIMARY KEY, Name VARCHAR2(100), Designation VARCHAR2(50))");

            stmt.executeUpdate("CREATE TABLE IssueInfo (" +
                    "IssueID NUMBER PRIMARY KEY, BookID NUMBER, ReaderID NUMBER, IssueDate DATE)");

            stmt.executeUpdate("CREATE TABLE ReturnBook (" +
                    "ReturnID NUMBER PRIMARY KEY, BookName VARCHAR2(100), ReaderID NUMBER, ReturnDate DATE)");

            System.out.println("Tables created successfully.");

            stmt.executeUpdate("INSERT INTO Books VALUES (seq_book_id.NEXTVAL, 'Java Programming', 'James', 'Sun Microsystems', TO_DATE('2020-05-01', 'YYYY-MM-DD'), 499.50, 5)");
            stmt.executeUpdate("INSERT INTO Books VALUES (seq_book_id.NEXTVAL, 'Database Systems', 'C.J. Date', 'Pearson', TO_DATE('2021-08-15', 'YYYY-MM-DD'), 599.99, 7)");
            stmt.executeUpdate("INSERT INTO Books VALUES (seq_book_id.NEXTVAL, 'oop', 'ME', 'YOU', TO_DATE('2022-08-15', 'YYYY-MM-DD'), 600.99, 7)");

   stmt.executeUpdate("INSERT INTO Readers VALUES (seq_reader_id.NEXTVAL, 'Alice ali')");
   stmt.executeUpdate("INSERT INTO Readers VALUES (seq_reader_id.NEXTVAL, 'Bob Johnson')");

   stmt.executeUpdate("INSERT INTO Staff VALUES (seq_staff_id.NEXTVAL, 'Robert Lee', 'Librarian')");
   stmt.executeUpdate("INSERT INTO Staff VALUES (seq_staff_id.NEXTVAL, 'Sara Kim', 'Assistant')");

   stmt.executeUpdate("INSERT INTO IssueInfo VALUES (seq_issue_id.NEXTVAL, 1001, 2001, TO_DATE('2024-01-10', 'YYYY-MM-DD'))");
   stmt.executeUpdate("INSERT INTO IssueInfo VALUES (seq_issue_id.NEXTVAL, 1002, 2002, TO_DATE('2024-01-15', 'YYYY-MM-DD'))");

   stmt.executeUpdate("INSERT INTO ReturnBook VALUES (seq_return_id.NEXTVAL, 'Java Programming', 2001, TO_DATE('2024-02-10', 'YYYY-MM-DD'))");
   stmt.executeUpdate("INSERT INTO ReturnBook VALUES (seq_return_id.NEXTVAL, 'Database Systems', 2002, TO_DATE('2024-02-20', 'YYYY-MM-DD'))");

            System.out.println("All tables created and data inserted successfully!");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s\n", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
