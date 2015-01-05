package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import code.SmartTVViewer;

import com.sun.jna.NativeLibrary;

import tv.TVChannel;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import user.Child;
import user.ChildManagement;
import user.Parent;
import user.UserManagement;
import util.Initializer;

public class SmartTVViewerView {

    public static SmartTVViewerView smartTVViewerView;
    private JFrame frmSTVV;
    private static int currentChannelPlaying;
    private static MediaPlayerPanel player;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    smartTVViewerView = new SmartTVViewerView();
		    smartTVViewerView.frmSTVV.setVisible(true);
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
	if(UserManagement.isChild()) {
	    SmartTVViewer.startTimeRestrictionCheck();
	}

	currentChannelPlaying = -1;
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	player = new MediaPlayerPanel();
	DefaultListModel<TVChannel> listModel = new DefaultListModel<TVChannel>();

	for(int i = 0; i < Initializer.tvChannelList.size(); i++) {
	    listModel.addElement(Initializer.tvChannelList.get(i));
	}

	frmSTVV = new JFrame();
	frmSTVV.setTitle("SmartTVViewer");
	frmSTVV.setBounds(100, 100, 650, 450);
	frmSTVV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JMenuBar menuBar = new JMenuBar();
	frmSTVV.setJMenuBar(menuBar);

	JMenu mnMenu = new JMenu("Menu");
	menuBar.add(mnMenu);

	JMenuItem mntmManageChild = new JMenuItem("Manage Child");
	mnMenu.add(mntmManageChild);
	if(UserManagement.isChild()) {
	    mntmManageChild.setEnabled(false);
	}

	JMenuItem mntmViewTvseries = new JMenuItem("View TVSeries");
	mnMenu.add(mntmViewTvseries);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setPreferredSize(new Dimension(100, 100));
	frmSTVV.getContentPane().add(scrollPane, BorderLayout.EAST);

	JList<TVChannel> listChannels = new JList<TVChannel>(listModel);
	listChannels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scrollPane.setViewportView(listChannels);

	frmSTVV.getContentPane().add(player, BorderLayout.CENTER);
	player.setVisible(true);
	frmSTVV.setVisible(true);

	listChannels.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent evt) {
		@SuppressWarnings("rawtypes")
		JList list = (JList) evt.getSource();
		if(evt.getClickCount() == 2) {
		    int index = list.locationToIndex(evt.getPoint());

		    if(currentChannelPlaying == index) {
			player.stop();
			currentChannelPlaying = -1;
		    }
		    else {
			if(!UserManagement.isChild()
				|| !((Child) UserManagement.user)
					.getBannedChannels().contains(
						listModel.get(index))) {
			    currentChannelPlaying = index;
			    File path = listModel.elementAt(index).getFile();
			    player.play(path.getAbsolutePath());
			}else{
			    JOptionPane.showMessageDialog(frmSTVV,
				    "You are not allowed to watch this Channel!");
			}
		    }
		}
	    }
	});

	mntmManageChild.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		ArrayList<Child> childrenList = ((Parent) (UserManagement.user))
			.getChildren();

		if(childrenList.size() > 0) {
		    Child[] children = new Child[childrenList.size()];

		    for(int i = 0; i < childrenList.size(); i++) {
			children[i] = childrenList.get(i);
		    }

		    Child child = (Child) JOptionPane.showInputDialog(frmSTVV,
			    "Choose Child to organize:", "Choose Child",
			    JOptionPane.PLAIN_MESSAGE, null, children,
			    children[0]);

		    if(child != null) {
			ChildManagement.getRestrictions(child);
			ChildManagement.selectedChild = child;
			ChildRestrictionView.main(null);
		    }
		}
		else {
		    JOptionPane.showMessageDialog(frmSTVV,
			    "This Parent has no children attached");
		}
	    }
	});

	mntmViewTvseries.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		TVSeriesView.main(null);
	    }
	});
    }

    public static void displayReminder(String message) {
	JLabel lblReminder = new JLabel(message);
	smartTVViewerView.frmSTVV.getContentPane().add(lblReminder,
		BorderLayout.NORTH);
	smartTVViewerView.frmSTVV.validate();

	try {
	    Thread.sleep(10000);
	    smartTVViewerView.frmSTVV.getContentPane().remove(lblReminder);
	    smartTVViewerView.frmSTVV.validate();
	} catch(InterruptedException e) {
	    e.printStackTrace();
	}
    }
    
    public static void notInTimeRestriction(JFrame frame){
	if(frame == null){
	    frame = smartTVViewerView.frmSTVV;
	}
	if(player != null && currentChannelPlaying >= 0){
	    player.stop();
	}
	JOptionPane.showMessageDialog(frame,
		    "You are no longer in the Time-Restriction-Window!\nProgram will close");
	exitProgram();
    }
    
    public static void exitProgram(){
	System.exit(0);
    }
}

@SuppressWarnings("serial")
class MediaPlayerPanel extends JPanel {

    // private File vlcInstallPath = new File("C:/Program Files/VideoLAN/VLC");
    private File vlcInstallPath = new File("lib");
    private EmbeddedMediaPlayer player;

    public MediaPlayerPanel() {
	NativeLibrary.addSearchPath("libvlc", vlcInstallPath.getAbsolutePath());
	EmbeddedMediaPlayerComponent videoCanvas = new EmbeddedMediaPlayerComponent();
	this.setLayout(new BorderLayout());
	this.add(videoCanvas, BorderLayout.CENTER);
	this.player = videoCanvas.getMediaPlayer();
	this.player.setRepeat(true);
    }

    public void play(String media) {
	player.prepareMedia(media);
	player.parseMedia();
	player.play();
    }

    public void stop() {
	player.stop();
    }
}
