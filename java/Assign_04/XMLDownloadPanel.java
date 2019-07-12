
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.Box;
import java.awt.Dimension;

//a subclass of JPanel, and contain most of the user interface for this assignment. 
//It will handle action events from the “Get Albums” button.
public class XMLDownloadPanel extends JPanel implements ActionListener {

	// declare data member and initialize
	private JButton getAlbums = new JButton("Get Albums");

	public JLabel elapsedTime = new JLabel("Elapsed Time: 00:00");
	// 10 rows and 60 cols for JTextArea

	// Jtable for extra credit
	float[] columnWidthPercentage = {40.0f, 30.0f, 20.0f, 10.0f};
	public Object[] colNames = { "Album Name", "Artist", "Genre", "Album Cover" };

        //jtable to hold album information
	public JTable albumDataText = new JTable();

	// JTable and its usage were explained by one of ’s friend 
	DefaultTableModel model = null;

        //xml downloader task manager
	private XMLDownloadTask XMLDownloadTaskObject; 

	private String feedType = "new-music";
	private String resultsLimit = "10";
	private boolean explicit = true;


        //constructor 
	public XMLDownloadPanel() {
		createAndShowGUI();
		addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            resizeColumns();
	        }
	    });

	}

 
        //create GUI
	private void createAndShowGUI() {

		// set layout and add components
		setLayout(new BorderLayout());

		// add scroll for text area
		add(new JScrollPane(albumDataText), BorderLayout.CENTER);

		// create new panel with flow layout
		JPanel panel2 = new JPanel(new FlowLayout());

		getAlbums.addActionListener(this); //add listner
		panel2.add(getAlbums); //add to panel
		panel2.add(elapsedTime); //add to panel

		// Add panel2 to page start of border layout
		add(panel2, BorderLayout.PAGE_START);
		
		//creating a model for Table and overridding the column types 
		model = new DefaultTableModel(colNames,0) {
			public Class getColumnClass(int column) {
				String s="";
				Icon i=new ImageIcon();
				switch (column) {
				//first three cases are string 
				case 0:
				case 1:
				case 2:
					return s.getClass();
				//last case is for image 
				case 3:
					return i.getClass();
				}
				return s.getClass();
			}
			
			//overriding the table to be non-editable 
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		//setting column names 
		//model.setColumnIdentifiers(colNames);
		albumDataText.setModel(model);
		albumDataText.setBackground(Color.LIGHT_GRAY);
		albumDataText.setForeground(Color.darkGray);
		albumDataText.setRowHeight(60);

	}

        //accessors
	public String getFeedType() {
		return feedType;
	}

	public String getResultsLimit() {
		return resultsLimit;
	}
        
	public boolean isExplicit() {
		return explicit;
	}
        //end accessors

        //mutators
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public void setResultsLimit(String resultsLimit) {
		this.resultsLimit = resultsLimit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}
        //end mutators


        //process listeners
	@Override
	public void actionPerformed(ActionEvent arg0) {
                if(arg0.getActionCommand() == "Get Albums"){
		  model.setRowCount(0);
		  download();
		}
	}

	//method to download from the URL 
	private void download() {
		String URLString = "https://rss.itunes.apple.com/api/v1/us/itunes-music/" + feedType + "/all/" + resultsLimit
				+ "/";
		if (explicit) {
			URLString += "explicit.atom";
		} else
			URLString += "non-explicit.atom";

		XMLDownloadTaskObject = new XMLDownloadTask(URLString, this);

		XMLDownloadTaskObject.addPropertyChangeListener(event -> {
			switch (event.getPropertyName()) {
			case "state":
                                //handle state change event
				switch ((StateValue) event.getNewValue()) {
				case DONE:
                                        //download done
					XMLDownloadTaskObject = null;
					getAlbums.setEnabled(true);
					break;
				case STARTED:
				case PENDING:
					getAlbums.setEnabled(false);
					break;
				}
				break;
			}
		});

		XMLDownloadTaskObject.execute();

	}
	
        //method to resize columns of table
	private void resizeColumns() {
	    int tW = albumDataText.getWidth(); //width of column
	    TableColumn column; 
	    TableColumnModel jTableColumnModel = albumDataText.getColumnModel();
	    int cantCols = jTableColumnModel.getColumnCount(); //column count
	    for (int i = 0; i < cantCols; i++) {
	        column = jTableColumnModel.getColumn(i);
	        int pWidth = Math.round(columnWidthPercentage[i] * tW); 
		//round width
	        column.setPreferredWidth(pWidth); //set width
	    }
	}
}
