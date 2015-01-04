package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import tv.TVBroadcast;
import tv.TVChannel;
import tv.TVSeries;
import util.Initializer;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.ComponentOrientation;
import javax.swing.JButton;

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

	frame = new JFrame();
	frame.setBounds(100, 100, 370, 300);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setPreferredSize(new Dimension(150, 500));
	frame.getContentPane().add(scrollPane, BorderLayout.WEST);

	JList<TVBroadcast> listChannels = new JList<TVBroadcast>(listModel);
	listChannels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scrollPane.setViewportView(listChannels);

	JPanel panel = new JPanel();
	panel.setPreferredSize(new Dimension(200, 100));
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
    }

}
