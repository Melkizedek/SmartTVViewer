package gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.ComponentOrientation;

import javax.swing.JSeparator;

import tv.TVChannel;
import util.Initializer;

public class ChildRestrictionView {

    private JFrame frmChildRestrictions;
    private JTextField tfFromTime;
    private JTextField tfToTime;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    ChildRestrictionView window = new ChildRestrictionView();
		    window.frmChildRestrictions.setVisible(true);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public ChildRestrictionView() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	DefaultListModel<TVChannel> listModel = new DefaultListModel<TVChannel>();

	for(int i = 0; i < Initializer.tvChannelList.size(); i++) {
	    listModel.addElement(Initializer.tvChannelList.get(i));
	}

	frmChildRestrictions = new JFrame();
	frmChildRestrictions.getContentPane().setComponentOrientation(
		ComponentOrientation.LEFT_TO_RIGHT);
	frmChildRestrictions.setTitle("Child Restrictions");
	frmChildRestrictions.setBounds(100, 100, 342, 371);
	frmChildRestrictions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frmChildRestrictions.getContentPane().setLayout(null);

	JLabel lblTimeWindow = new JLabel("Time:");
	lblTimeWindow.setBounds(5, 8, 67, 14);
	frmChildRestrictions.getContentPane().add(lblTimeWindow);

	JLabel lblFromTime = new JLabel("From: ");
	lblFromTime.setBounds(77, 8, 35, 14);
	frmChildRestrictions.getContentPane().add(lblFromTime);

	tfFromTime = new JTextField();
	tfFromTime.setBounds(110, 5, 86, 20);
	frmChildRestrictions.getContentPane().add(tfFromTime);
	tfFromTime.setColumns(10);

	JLabel lblToTime = new JLabel("To:");
	lblToTime.setBounds(201, 8, 28, 14);
	frmChildRestrictions.getContentPane().add(lblToTime);

	tfToTime = new JTextField();
	tfToTime.setBounds(227, 5, 86, 20);
	frmChildRestrictions.getContentPane().add(tfToTime);
	tfToTime.setColumns(10);

	JLabel lblMaximumTime = new JLabel("Maximum Time:");
	lblMaximumTime.setBounds(5, 33, 107, 14);
	frmChildRestrictions.getContentPane().add(lblMaximumTime);

	textField = new JTextField();
	textField.setBounds(110, 33, 86, 20);
	frmChildRestrictions.getContentPane().add(textField);
	textField.setColumns(10);

	JLabel lblBannedChannels = new JLabel("Banned Channels:");
	lblBannedChannels.setBounds(5, 58, 122, 20);
	frmChildRestrictions.getContentPane().add(lblBannedChannels);

	JSeparator separator = new JSeparator();
	separator.setBounds(0, 58, 326, 2);
	frmChildRestrictions.getContentPane().add(separator);

	JList<TVChannel> list = new JList<TVChannel>(listModel);
	list.setBounds(5, 77, 94, 245);
	list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	list.setSelectionModel(new DefaultListSelectionModel() {
	    @Override
	    public void setSelectionInterval(int index0, int index1) {
	        if(super.isSelectedIndex(index0)) {
	            super.removeSelectionInterval(index0, index1);
	        }
	        else {
	            super.addSelectionInterval(index0, index1);
	        }
	    }
	});
	
	frmChildRestrictions.getContentPane().add(list);
    }
}
