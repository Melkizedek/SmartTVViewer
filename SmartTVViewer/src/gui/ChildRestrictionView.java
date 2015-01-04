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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class ChildRestrictionView {

    private JFrame frmChildRestrictions;
    private JTextField tfFromTime;
    private JTextField tfToTime;
    private JTextField tfMaximumTime;

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
    @SuppressWarnings("serial")
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
	frmChildRestrictions.getContentPane().setLayout(new BorderLayout(0, 0));

	JPanel panel = new JPanel();
	frmChildRestrictions.getContentPane().add(panel, BorderLayout.NORTH);
	panel.setLayout(new BorderLayout(0, 0));

	JPanel panel_1 = new JPanel();
	FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
	flowLayout_1.setAlignment(FlowLayout.LEFT);
	panel.add(panel_1, BorderLayout.NORTH);

	JLabel lblTimeWindow = new JLabel("Time (hh:mm):");
	panel_1.add(lblTimeWindow);

	JLabel lblFromTime = new JLabel("From: ");
	panel_1.add(lblFromTime);

	tfFromTime = new JTextField();
	panel_1.add(tfFromTime);
	tfFromTime.setColumns(10);

	JLabel lblToTime = new JLabel("To:");
	panel_1.add(lblToTime);

	tfToTime = new JTextField();
	panel_1.add(tfToTime);
	tfToTime.setColumns(10);

	JPanel panel_2 = new JPanel();
	FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
	flowLayout.setAlignment(FlowLayout.LEFT);
	panel.add(panel_2, BorderLayout.SOUTH);

	JLabel lblMaximumTime = new JLabel("Maximum Time:");
	panel_2.add(lblMaximumTime);

	tfMaximumTime = new JTextField();
	panel_2.add(tfMaximumTime);
	tfMaximumTime.setColumns(10);

	JPanel panel_3 = new JPanel();
	panel_3.setPreferredSize(new Dimension(100, 100));
	frmChildRestrictions.getContentPane().add(panel_3, BorderLayout.WEST);
	panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	JLabel lblBannedChannels = new JLabel("Banned Channels:");
	panel_3.add(lblBannedChannels);

	JList<TVChannel> list = new JList<TVChannel>(listModel);
	list.setPreferredSize(new Dimension(100, 100));
	panel_3.add(list);
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
    }
}
