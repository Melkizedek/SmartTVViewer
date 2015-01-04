package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;

import java.awt.Window.Type;
import java.util.ArrayList;

import javax.swing.JPasswordField;

import database.DataBase;
import user.Parent;
import user.UserManagement;

public class CreateNewUserView {

    private JFrame frmCreateNewUser;
    private JTextField tfName;
    private JPasswordField tfPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    CreateNewUserView window = new CreateNewUserView();
		    window.frmCreateNewUser.setVisible(true);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public CreateNewUserView() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frmCreateNewUser = new JFrame();
	frmCreateNewUser.setResizable(false);
	frmCreateNewUser.setTitle("Create New User");
	frmCreateNewUser.setBounds(100, 100, 285, 164);
	frmCreateNewUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frmCreateNewUser.getContentPane().setLayout(null);

	JLabel lblName = new JLabel("Name: ");
	lblName.setBounds(10, 14, 71, 14);
	frmCreateNewUser.getContentPane().add(lblName);

	tfName = new JTextField();
	tfName.setColumns(10);
	tfName.setBounds(76, 11, 183, 20);
	frmCreateNewUser.getContentPane().add(tfName);

	JLabel lblPassword = new JLabel("Password: ");
	lblPassword.setBounds(10, 39, 71, 14);
	frmCreateNewUser.getContentPane().add(lblPassword);

	JCheckBox chckbxChildUser = new JCheckBox("Child User");
	chckbxChildUser.setBounds(10, 66, 103, 23);
	frmCreateNewUser.getContentPane().add(chckbxChildUser);

	ArrayList<Parent> parentList = DataBase.getInstance().getParents();
	Parent[] parents = new Parent[parentList.size()];
	for(int i = 0; i < parentList.size(); i++){
	    parents[i] = parentList.get(i);
	}

	JComboBox<Parent> cbParents = new JComboBox<Parent>(parents);
	cbParents.setBounds(76, 96, 183, 20);
	if(parents.length > 0) {
	    cbParents.setSelectedIndex(0);
	}
	frmCreateNewUser.getContentPane().add(cbParents);
	cbParents.setVisible(false);

	JLabel lblParent = new JLabel("Parent: ");
	lblParent.setBounds(10, 99, 46, 14);
	frmCreateNewUser.getContentPane().add(lblParent);
	lblParent.setVisible(false);

	JButton btnCreateUser = new JButton("Create User");
	btnCreateUser.setBounds(119, 66, 140, 23);
	frmCreateNewUser.getContentPane().add(btnCreateUser);

	tfPassword = new JPasswordField();
	tfPassword.setBounds(76, 36, 183, 20);
	frmCreateNewUser.getContentPane().add(tfPassword);

	btnCreateUser.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		if(tfName.getText().isEmpty()
			|| tfPassword.getPassword().length == 0) {
		    JOptionPane.showMessageDialog(frmCreateNewUser,
			    "Please enter a name and password!");
		    tfName.grabFocus();
		}
		else if(chckbxChildUser.isSelected() && parents.length == 0) {
		    JOptionPane.showMessageDialog(frmCreateNewUser,
			    "There are no Parents to assign!");
		    chckbxChildUser.setSelected(false);
		}
		else {
		    boolean successful = UserManagement.createNewUser(
			    tfName.getText(), tfPassword.getPassword(),
			    chckbxChildUser.isSelected(),
			    (Parent) cbParents.getSelectedItem());

		    if(successful) {
			frmCreateNewUser.dispose();
		    }
		    else {
			JOptionPane.showMessageDialog(frmCreateNewUser,
				"Username already in use!");
			tfName.setText("");
			tfPassword.setText("");
			tfName.grabFocus();
		    }
		}
	    }
	});

	chckbxChildUser.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {
		AbstractButton abstractButton = (AbstractButton) changeEvent
			.getSource();
		ButtonModel buttonModel = abstractButton.getModel();
		boolean selected = buttonModel.isSelected();

		if(selected) {
		    cbParents.setVisible(true);
		    lblParent.setVisible(true);
		}
		else {
		    cbParents.setVisible(false);
		    lblParent.setVisible(false);
		}
	    }
	});

    }
}
