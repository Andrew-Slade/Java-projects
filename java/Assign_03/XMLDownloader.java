
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class XMLDownloader extends JFrame implements ActionListener {

private XMLDownloadPanel XMLdownloadPanelObject = new XMLDownloadPanel();

	/**
	 * Launch the application.
	 */
         public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XMLDownloader frame = new XMLDownloader("CSCI 470 Assign3: iTunes Store Albums"); //constuct class
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public XMLDownloader(String title) {
		super(title); //set title for frame
		createAndShowGUI(); //instantiate

	}

	private void createAndShowGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500); //size
		createMenu(); //call create menu

		add(XMLdownloadPanelObject, BorderLayout.CENTER); //add panel

	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Type menu buttons
		JMenu typeMenu = new JMenu("Type");
		typeMenu.setToolTipText("Type");
		typeMenu.setMnemonic('t');
		menuBar.add(typeMenu);

		JRadioButtonMenuItem newMusicRButton = new JRadioButtonMenuItem("New Music");
		newMusicRButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		newMusicRButton.setSelected(true); //set state
		newMusicRButton.addActionListener(this); //add listener
		typeMenu.add(newMusicRButton); //add to menu

		JRadioButtonMenuItem recentReleasesRButton = new JRadioButtonMenuItem("Recent Releases");
		recentReleasesRButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		recentReleasesRButton.addActionListener(this); //add listener
		typeMenu.add(recentReleasesRButton); //add to menu

		JRadioButtonMenuItem topAlbumsRButton = new JRadioButtonMenuItem("Top Albums");
		topAlbumsRButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
		topAlbumsRButton.addActionListener(this); //add listener
		typeMenu.add(topAlbumsRButton); //add to menu

		// group radio buttons together
		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(newMusicRButton); 
		typeGroup.add(recentReleasesRButton);
		typeGroup.add(topAlbumsRButton);

		// limit menu buttons
		JMenu limitMenu = new JMenu("Limit");
		limitMenu.setToolTipText("Limit");
		limitMenu.setMnemonic('l');
		menuBar.add(limitMenu); //add to menu

		JRadioButtonMenuItem l10RButton = new JRadioButtonMenuItem("10");
		l10RButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
		l10RButton.setSelected(true); //set default
		l10RButton.addActionListener(this); //add listener
		limitMenu.add(l10RButton); //add to menu

		JRadioButtonMenuItem l25RButton = new JRadioButtonMenuItem("25");
		l25RButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
		l25RButton.addActionListener(this); //add listener
		limitMenu.add(l25RButton); //add to menu

		JRadioButtonMenuItem l50RButton = new JRadioButtonMenuItem("50");
		l50RButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		l50RButton.addActionListener(this); //add listener
		limitMenu.add(l50RButton); //add to menu

		JRadioButtonMenuItem l100RButton = new JRadioButtonMenuItem("100");
		l100RButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
		l100RButton.addActionListener(this); //add listener
		limitMenu.add(l100RButton); //add to menu

		// group radio buttons together
		ButtonGroup limitGroup = new ButtonGroup();
		limitGroup.add(l10RButton); 
		limitGroup.add(l25RButton);
		limitGroup.add(l50RButton);
		limitGroup.add(l100RButton);

		// explicit menu buttons
		JMenu explicitMenu = new JMenu("Explicit");
		explicitMenu.setToolTipText("Explicit");
		explicitMenu.setMnemonic('e');
		menuBar.add(explicitMenu); //add to menu

		JRadioButtonMenuItem yesRButton = new JRadioButtonMenuItem("Yes");
		yesRButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0));
		yesRButton.setSelected(true); //set deafult
		yesRButton.addActionListener(this); //add listener
		explicitMenu.add(yesRButton); //add to menu

		JRadioButtonMenuItem noRButton = new JRadioButtonMenuItem("No");
		noRButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0));
		noRButton.addActionListener(this); //add listener
		explicitMenu.add(noRButton); //add to menu

		// group radio buttons together
		ButtonGroup explicitGroup = new ButtonGroup();
		explicitGroup.add(yesRButton);
		explicitGroup.add(noRButton);

	}

	@Override
        /* Process actions performed 
	 * inside of the program
	 */
	public void actionPerformed(ActionEvent e) {

                //handle action events
		switch (e.getActionCommand()) {
		case "New Music":
			XMLdownloadPanelObject.setFeedType("new-music");
			break;

		case "Recent Releases":
			XMLdownloadPanelObject.setFeedType("recent-releases");
			break;

		case "Top Albums":
			XMLdownloadPanelObject.setFeedType("top-albums");
			break;

		case "10":
			XMLdownloadPanelObject.setResultsLimit("10");
			break;

		case "25":
			XMLdownloadPanelObject.setResultsLimit("25");
			break;

		case "50":
			XMLdownloadPanelObject.setResultsLimit("50");
			break;

		case "100":
			XMLdownloadPanelObject.setResultsLimit("100");
			break;

		case "Yes":
			XMLdownloadPanelObject.setExplicit(true);
			break;

		case "No":
			XMLdownloadPanelObject.setExplicit(false);
			break;
		}

	}

}
