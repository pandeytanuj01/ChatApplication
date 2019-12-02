import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage implements ActionListener {
    private final String url = "jdbc:mysql://localhost:3306/UserDatabase";
    private JFrame LoginFrame;
    private JLabel Loginform;
    private JLabel username, password;
    private JTextField userfield;
    private JPasswordField passfield;
    private JButton Loginbtn;
    private boolean login = false;

    private void LoginForm() {
        LoginFrame = new JFrame();
        LoginFrame.setLayout(null);
        LoginFrame.setTitle("ChatApplication");
        Loginform = new JLabel("LOGIN FORM");
        username = new JLabel("E-Mail");
        password = new JLabel("Password");
        userfield = new JTextField(15);
        userfield.setToolTipText("Enter your Email Address!!");
        passfield = new JPasswordField(15);
        passfield.setToolTipText("Enter your Password");
        Loginbtn = new JButton("Login");
        LoginFrame.setSize(365, 350);
        LoginFrame.setLocation(700, 300);
        Loginform.setBounds(90, 15, 200, 50);
        userfield.setBounds(140, 105, 150, 30);
        passfield.setBounds(140, 155, 150, 30);
        Loginbtn.setBounds(120, 220, 80, 30);
        username.setBounds(30, 108, 80, 30);
        password.setBounds(30, 153, 80, 30);
        LoginFrame.setBackground(Color.blue);
        LoginFrame.setResizable(false);
        LoginFrame.getContentPane().setBackground(new Color(66, 165, 245));
        Loginform.setFont(new Font("Open Sans", Font.BOLD, 28));
        Loginform.setForeground(new Color(244, 81, 30));
        username.setForeground(new Color(33, 33, 33));
        password.setForeground(new Color(33, 33, 33));
        Loginbtn.setBackground(new Color(255, 112, 67));
        Loginbtn.addActionListener(this);
        LoginFrame.add(Loginform);
        LoginFrame.add(username);
        LoginFrame.add(userfield);
        LoginFrame.add(password);
        LoginFrame.add(passfield);
        LoginFrame.add(Loginbtn);
    }

    private boolean DataEntry() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "SELECT emailID, password FROM userdata WHERE emailID='" + userfield.getText() + "' AND password = '" + String.valueOf(passfield.getPassword()) + "'\n";
        Connection connection = DriverManager.getConnection(url, "root", "12345678");
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        login = resultSet.next();
        return login;
    }

    LoginPage() {
        LoginForm();
        LoginFrame.setVisible(true);
        LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            if (DataEntry()) {
                LoginFrame.dispose();
                JOptionPane.showMessageDialog(null, "Login Successful!!");
                Thread ServerThread = new Thread(new MyServer());
                ServerThread.start();
                Thread ClientThread = new Thread(new MyClient());
                ClientThread.start();
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter correct Username or Password", "Login Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

class LoginPageRun {
    public static void main(String[] args) {
        new LoginPage();
    }
}

// ttanuj09@gmail.com
// JaiShriRam
