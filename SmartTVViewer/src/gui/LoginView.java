package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import user.Child;
import user.UserManagement;

@SuppressWarnings("serial")
public class LoginView extends JPanel {

    private JFrame frmLogin;
    private JTextField tfName;
    private JPasswordField tfPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    LoginView window = new LoginView();
		    window.frmLogin.setVisible(true);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public LoginView() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frmLogin = new JFrame();
	frmLogin.setResizable(false);
	frmLogin.setTitle("Manual Login");
	frmLogin.setBounds(100, 100, 291, 137);
	frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frmLogin.getContentPane().setLayout(null);
	frmLogin.getContentPane().setBackground(new Color(138, 43, 226));

	JLabel lblName = new JLabel("E-Mail: ");
	lblName.setBounds(10, 14, 71, 14);
	lblName.setForeground(Color.WHITE);
	frmLogin.getContentPane().add(lblName);

	JLabel lblPassword = new JLabel("Password: ");
	lblPassword.setBounds(10, 39, 71, 14);
	lblPassword.setForeground(Color.WHITE);
	frmLogin.getContentPane().add(lblPassword);

	tfName = new JTextField();
	tfName.setBounds(76, 11, 189, 20);
	frmLogin.getContentPane().add(tfName);
	tfName.setColumns(10);

	JButton btnLogin = new JButton("Login");
	btnLogin.setBounds(10, 64, 89, 23);
	frmLogin.getContentPane().add(btnLogin);

	btnLogin.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		if(tfName.getText().isEmpty()
			|| tfPassword.getPassword().length == 0) {
		    JOptionPane.showMessageDialog(frmLogin,
			    "Please enter a name and password!");
		    tfName.grabFocus();
		}
		else {
		    boolean successful = UserManagement.login(tfName.getText(),
			    tfPassword.getPassword());

		    if(successful) {
			if(UserManagement.isChild()
				&& (!((Child) UserManagement.user)
					.isInTimeRestriction() || !((Child) UserManagement.user)
					.isUnderMaxTime())) {
			    SmartTVViewerView.notInTimeRestriction(frmLogin);
			}
			frmLogin.dispose();
			SmartTVViewerView.main(null);
		    }
		    else {
			JOptionPane.showMessageDialog(frmLogin,
				"Wrong username or password!");
			tfName.setText("");
			tfPassword.setText("");
			tfName.grabFocus();
		    }
		}
	    }
	});

	JButton btnCreateAccount = new JButton("Create New Account");
	btnCreateAccount.setBounds(109, 64, 156, 23);
	frmLogin.getContentPane().add(btnCreateAccount);

	tfPassword = new JPasswordField();
	tfPassword.setBounds(76, 36, 189, 20);
	frmLogin.getContentPane().add(tfPassword);

	btnCreateAccount.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		CreateNewUserView.main(null);
	    }
	});
    }
}