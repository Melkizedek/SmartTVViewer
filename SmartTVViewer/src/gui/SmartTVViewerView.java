package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import code.UserManagement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import tv.TVChannel;
import util.Initializer;

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
	DefaultListModel<TVChannel> listModel = new DefaultListModel<TVChannel>();
	
	for(int i = 0; i < Initializer.tvChannelList.size(); i++){
	    listModel.addElement(Initializer.tvChannelList.get(i));
	}

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
	
	JMenuItem mntmViewTvseries = new JMenuItem("View TVSeries");
	mnMenu.add(mntmViewTvseries);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setPreferredSize(new Dimension(100, 100));
	frmSTVV.getContentPane().add(scrollPane, BorderLayout.EAST);
	
	JList<TVChannel> listChannels = new JList<TVChannel>(listModel);
	listChannels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scrollPane.setViewportView(listChannels);
	
	listChannels.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent evt) {
	        JList list = (JList)evt.getSource();
	        if (evt.getClickCount() == 2) {
	            int index = list.locationToIndex(evt.getPoint());
	            try {
			Desktop.getDesktop().open(listModel.elementAt(index).getFile());
		    } catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
	        } 
	    }
	});

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
	
	mntmViewTvseries.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		TVSeriesView.main(null);
	    }
	});
    }

}
