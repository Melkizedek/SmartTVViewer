package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import code.UserManagement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class SmartTVViewerView {

    private JFrame frmSTVV;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    SmartTVViewerView window = new SmartTVViewerView();
		    window.frmSTVV.setVisible(true);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public SmartTVViewerView() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	listModel.addElement("Jane Doe");
	listModel.addElement("John Smith222222222222222222222222222222222222");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	listModel.addElement("Kathy Green");
	

	frmSTVV = new JFrame();
	frmSTVV.setTitle("SmartTVViewer");
	frmSTVV.setBounds(100, 100, 450, 300);
	frmSTVV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JMenuBar menuBar = new JMenuBar();
	frmSTVV.setJMenuBar(menuBar);

	JMenu mnMenu = new JMenu("Menu");
	menuBar.add(mnMenu);

	JMenuItem mntmOrganizeChild = new JMenuItem("Organize Child");
	mnMenu.add(mntmOrganizeChild);
	if(UserManagement.isChild()) {
	    mntmOrganizeChild.setEnabled(false);
	}
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setPreferredSize(new Dimension(100, 100));
	frmSTVV.getContentPane().add(scrollPane, BorderLayout.EAST);
	
	JList listChannels = new JList(listModel);
	scrollPane.setViewportView(listChannels);

	mntmOrganizeChild.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		Object[] possibilities = { "ham", "spam", "yam" };
		String s = (String) JOptionPane.showInputDialog(frmSTVV,
			"Choose Child to organize:", "Choose Child",
			JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
		
		if(s != null && !s.isEmpty() && s.length() > 0){
		    ChildRestrictionView.main(null);
		}
	    }
	});
    }

}
