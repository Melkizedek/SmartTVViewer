package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
	PlayerPanel player = new PlayerPanel();
	DefaultListModel<TVChannel> listModel = new DefaultListModel<TVChannel>();

	for(int i = 0; i < Initializer.tvChannelList.size(); i++) {
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
	
	frmSTVV.getContentPane().add(player, BorderLayout.CENTER);
	player.setVisible(true);
	frmSTVV.setVisible(true);

	listChannels.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent evt) {
		JList list = (JList) evt.getSource();
		if(evt.getClickCount() == 2) {
		    int index = list.locationToIndex(evt.getPoint());
		    File path = listModel.elementAt(index).getFile();
		    player.play(path.getAbsolutePath());
		}
	    }
	});

	mntmOrganizeChild.addActionListener(new ActionListener() {
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

}

class PlayerPanel extends JPanel {

    private File vlcInstallPath = new File("C:/Program Files/VideoLAN/VLC");
    private EmbeddedMediaPlayer player;

    public PlayerPanel() {
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
}
