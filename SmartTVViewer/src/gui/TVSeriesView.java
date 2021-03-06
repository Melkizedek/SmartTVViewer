package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import tv.TVBroadcast;
import util.Initializer;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import code.SmartTVViewer;

public class TVSeriesView {

    private JFrame frame;
    private JTextField tfReminder;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    TVSeriesView window = new TVSeriesView();
		    window.frame.setVisible(true);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public TVSeriesView() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	DefaultListModel<TVBroadcast> listModel = new DefaultListModel<TVBroadcast>();

	for(int i = 0; i < Initializer.tvChannelList.size(); i++) {
	    for(int j = 0; j < Initializer.tvChannelList.get(i).getBroadcasts()
		    .size(); j++) {
		listModel.addElement(Initializer.tvChannelList.get(i)
			.getBroadcasts().get(j));
	    }
	}

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	frame = new JFrame();
	frame.setBounds(100, 100, 420, 320);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setLocation(screenSize.width/2 - frame.getWidth()/2, screenSize.height/2 - frame.getHeight()/2);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setPreferredSize(new Dimension(170, 500));
	frame.getContentPane().add(scrollPane, BorderLayout.WEST);

	JList<TVBroadcast> listChannels = new JList<TVBroadcast>(listModel);
	listChannels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scrollPane.setViewportView(listChannels);

	JPanel panel = new JPanel();
	panel.setPreferredSize(new Dimension(220, 140));
	frame.getContentPane().add(panel, BorderLayout.EAST);

	JLabel lblNewLabel = new JLabel("Set Reminder:");
	lblNewLabel.setVerticalAlignment(SwingConstants.TOP);

	tfReminder = new JTextField();
	tfReminder.setColumns(10);

	JLabel lblMinutesBeforeThe = new JLabel(
		"minutes before the Show starts");
	panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	panel.add(lblNewLabel);
	panel.add(tfReminder);
	panel.add(lblMinutesBeforeThe);

	JButton btnSetReminder = new JButton("Set Reminder");
	panel.add(btnSetReminder);

	btnSetReminder.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		if(listChannels.getSelectedValue() != null) {
		    int reminder = 0;
		    try {
			reminder = Integer.valueOf(tfReminder.getText());
			SmartTVViewer.setReminder(
				listChannels.getSelectedValue(), reminder);
		    } catch(Exception e) {
			JOptionPane.showMessageDialog(frame,
				"Input is not a valid number!");
			tfReminder.setText("");
			tfReminder.grabFocus();
		    }
		}
		else {
		    JOptionPane.showMessageDialog(frame,
			    "No Broadcast selected!");
		}

	    }
	});
    }

}
