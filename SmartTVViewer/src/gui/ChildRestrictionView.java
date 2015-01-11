package gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.ComponentOrientation;

import tv.TVChannel;
import user.ChildManagement;
import util.Initializer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JButton;

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
	ArrayList<TVChannel> bannedChannels = ChildManagement.selectedChild
		.getBannedChannels();
	DefaultListModel<TVChannel> listModel = new DefaultListModel<TVChannel>();

	for(int i = 0; i < Initializer.tvChannelList.size(); i++) {
	    listModel.addElement(Initializer.tvChannelList.get(i));
	}

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	frmChildRestrictions = new JFrame();
	frmChildRestrictions.getContentPane().setComponentOrientation(
		ComponentOrientation.LEFT_TO_RIGHT);
	frmChildRestrictions.setTitle("Child Restrictions");
	frmChildRestrictions.setBounds(100, 100, 419, 293);
	frmChildRestrictions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frmChildRestrictions.getContentPane().setLayout(new BorderLayout(0, 0));
	frmChildRestrictions.setLocation(screenSize.width / 2
		- frmChildRestrictions.getWidth() / 2, screenSize.height / 2
		- frmChildRestrictions.getHeight() / 2);

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
	lblBannedChannels.setFont(new Font("Tahoma", Font.BOLD, 11));
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

	if(bannedChannels != null) {
	    for(int i = 0; i < bannedChannels.size(); i++) {
		for(int j = 0; j < listModel.size(); j++) {
		    if(listModel.get(j).toString()
			    .equals(bannedChannels.get(i).toString())) {
			list.getSelectionModel().addSelectionInterval(j, j);
			break;
		    }
		}
	    }
	}

	JPanel panel_4 = new JPanel();
	FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
	flowLayout_2.setAlignment(FlowLayout.RIGHT);
	frmChildRestrictions.getContentPane().add(panel_4, BorderLayout.SOUTH);

	if(ChildManagement.selectedChild.getFromTime() != null) {
	    String minute = String.valueOf(ChildManagement.selectedChild
		    .getFromTime().get(Calendar.MINUTE));
	    if(minute.length() == 1) {
		minute = '0' + minute;
	    }

	    tfFromTime.setText(String.valueOf(ChildManagement.selectedChild
		    .getFromTime().get(Calendar.HOUR_OF_DAY)) + ":" + minute);
	}
	if(ChildManagement.selectedChild.getFromTime() != null) {
	    String minute = String.valueOf(ChildManagement.selectedChild
		    .getToTime().get(Calendar.MINUTE));
	    if(minute.length() == 1) {
		minute = '0' + minute;
	    }

	    tfToTime.setText(String.valueOf(ChildManagement.selectedChild
		    .getToTime().get(Calendar.HOUR_OF_DAY)) + ":" + minute);
	}
	if(ChildManagement.selectedChild.getMaxTime() >= 0) {
	    int hour = (int) ChildManagement.selectedChild.getMaxTime() / 3600000;
	    int minutes = (int) (((ChildManagement.selectedChild.getMaxTime() % 3600000) / 60000));

	    String minutesString = String.valueOf(minutes);
	    if(minutesString.length() == 1) {
		minutesString = '0' + minutesString;
	    }

	    if(hour > 0 || minutes > 0) {
		tfMaximumTime.setText(hour + ":" + minutesString);
	    }
	}

	JButton btnSetRestrictions = new JButton("Set Restrictions");
	panel_4.add(btnSetRestrictions);

	btnSetRestrictions.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {

		if((!tfFromTime.getText().isEmpty() && tfToTime.getText()
			.isEmpty())
			|| (tfFromTime.getText().isEmpty() && !tfToTime
				.getText().isEmpty())) {
		    JOptionPane.showMessageDialog(frmChildRestrictions,
			    "Either From- or To-Time is empty!");
		}
		else {
		    long maxTime = -1;
		    ArrayList<TVChannel> channels = null;
		    Calendar fromTime = null;
		    Calendar toTime = null;

		    if(!tfFromTime.getText().isEmpty()
			    && !tfToTime.getText().isEmpty()) {
			try {
			    String[] splitFromTime = tfFromTime.getText()
				    .split(":");
			    String[] splitToTime = tfToTime.getText()
				    .split(":");

			    if(splitFromTime.length != 2
				    || splitToTime.length != 2
				    || splitFromTime[0].length() < 1
				    || splitFromTime[0].length() > 2
				    || splitFromTime[1].length() != 2
				    || splitToTime.length != 2
				    || splitToTime[0].length() < 1
				    || splitToTime[0].length() > 2
				    || splitToTime[1].length() != 2) {
				JOptionPane
					.showMessageDialog(
						frmChildRestrictions,
						"Invalid Time Input (Must be hh:mm or h:mm)!");
				return;
			    }

			    fromTime = Calendar.getInstance();
			    fromTime.set(Calendar.HOUR_OF_DAY,
				    Integer.valueOf(splitFromTime[0]));
			    fromTime.set(Calendar.MINUTE,
				    Integer.valueOf(splitFromTime[1]));

			    toTime = Calendar.getInstance();
			    toTime.set(Calendar.HOUR_OF_DAY,
				    Integer.valueOf(splitToTime[0]));
			    toTime.set(Calendar.MINUTE,
				    Integer.valueOf(splitToTime[1]));
			} catch(Exception e) {
			    JOptionPane
				    .showMessageDialog(frmChildRestrictions,
					    "Invalid Time Input (Must be hh:mm or h:mm)!");
			    return;
			}
		    }
		    if(!tfMaximumTime.getText().isEmpty()) {
			try {
			    String[] splitMaxTime = tfMaximumTime.getText()
				    .split(":");

			    if(splitMaxTime.length != 2
				    || splitMaxTime[0].length() < 1
				    || splitMaxTime[0].length() > 2
				    || splitMaxTime[1].length() != 2) {
				JOptionPane
					.showMessageDialog(
						frmChildRestrictions,
						"Invalid Time Input (Must be hh:mm or h:mm)!");
				return;
			    }

			    Calendar tmpTime = Calendar.getInstance();
			    tmpTime.set(Calendar.HOUR_OF_DAY,
				    Integer.valueOf(splitMaxTime[0]));
			    tmpTime.set(Calendar.MINUTE,
				    Integer.valueOf(splitMaxTime[1]));

			    maxTime = 3600000
				    * tmpTime.get(Calendar.HOUR_OF_DAY) + 60000
				    * tmpTime.get(Calendar.MINUTE);
			} catch(Exception e) {
			    JOptionPane
				    .showMessageDialog(frmChildRestrictions,
					    "Invalid Time Input (Must be hh:mm or h:mm)!");
			    return;
			}
		    }
		    if(!list.isSelectionEmpty()) {
			channels = (ArrayList<TVChannel>) list
				.getSelectedValuesList();
		    }

		    ChildManagement.setRestrictions(fromTime, toTime, maxTime,
			    channels);
		    frmChildRestrictions.dispose();
		}
	    }
	});
    }
}
