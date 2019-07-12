
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLDownloadTask extends SwingWorker<List<Album>, Album> {

	// declare data members
	String URLstring;
	ArrayList<Album> albums;

	// delegate variable to hold obj ref
	private XMLDownloadPanel delegate;

	// constructor
	public XMLDownloadTask(String uRLstring, XMLDownloadPanel delegate) {
		// initialize
		URLstring = uRLstring;
		this.delegate = delegate;
		albums = new ArrayList<>();

	}

	// the following method was adapted from "Downloading XML from a Web Page" on Blackboard
        //split responsibility 
	@Override
	protected List<Album> doInBackground() {

		// start time
		long downloadStartTime = System.nanoTime();

		// ***********
		// Downloads XML data as a string
		// ***********

		// DeclareavariabletoholdthedownloadedXMLstring
		String xmlString = null;
		// DeclareanobjectreferenceforanHttpURLConnectionobject
		HttpURLConnection connection = null;

		try {
			// Create a URL object from a String that contains a valid URL
			URL url = new URL(URLstring);
			// Open an HTTP connection for the URL
			connection = (HttpURLConnection) url.openConnection();
			// Set HTTP request method
			connection.setRequestMethod("GET");
			// If the HTTP status code is 200, we have successfully connected
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Use a mutable StringBuilder to store the downloaded text
				StringBuilder xmlResponse = new StringBuilder();
				// Create a BufferedReader to read the lines of XML from the
				// connection's input stream
				BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				// Read lines of XML and append them to the StringBuilder
				// object until the end of the stream is reached
				String strLine; //holds the response 
				while ((strLine = input.readLine()) != null) {
					xmlResponse.append(strLine);
				}
				// Convert the StringBuilder object to a String
				xmlString = xmlResponse.toString();


				/**
				 * Do something to process the XML in xmlString
				 */
				// Close the input stream
				input.close();
			}
		} catch (MalformedURLException e) {
			// Handle MalformedURLException
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// Handle IOException
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// Handle any other exceptions thrown by the code that
			// processes xmlString
			System.out.println(e.getMessage());
		} finally {
			// close connection
			if (connection != null) {
				connection.disconnect();
			}
		}

		// ***********
		// Creates a SAX parser
		// ***********
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			saxParser.parse(new InputSource(new ByteArrayInputStream(xmlString.getBytes("utf-8"))), new AlbumHandler());

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		// end download time
		long downloadEndTime = System.nanoTime();

		// time elapsed
		long downloadTime = (downloadEndTime - downloadStartTime) / 1000000000;
		// time format
		long dMin = downloadTime / 60; // quotient is min
		long dSec = downloadTime % 60; // remainder is sec

		String formattedTime = ""; //format time output
		if (dMin >= 10) {
			formattedTime += dMin + ":";
		} else {
			formattedTime += "0" + dMin + ":";
		}

		if (dSec >= 10) {
			formattedTime += dSec;
		} else {
			formattedTime += "0" + dSec;
		}

		delegate.elapsedTime.setText("Elapsed Time: " + formattedTime);

		return albums;

	}

	// the following method is adapted from assignment sheet
        //handle image scaling
	private Image getScaledImage(String srcImgUrl) {
		Image srcImg=null;
		try {
			URL url=new URL(srcImgUrl); //get url
			srcImg=ImageIO.read(url); //get image url
		
			BufferedImage resizedImage = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB); //resize image
			Graphics2D g2 = resizedImage.createGraphics();//create graphics
			
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(srcImg,0,0,50,50,null);
			g2.dispose();
			
			return resizedImage;
		}catch(IOException ex) {
			return null;
		}
	}
	
	@Override
        //process the images
	protected void process(List<Album> albumList) {
		for (Album a : albumList) {
			Image img = getScaledImage(a.getThumbnailUrl());
			Icon thumbnail= new ImageIcon(img);
			delegate.model.addRow(new Object[] { a.getAlbumName(), a.getArtistName(), a.getGenre(), thumbnail });
		}

	}

	// the following class is adapted from "Parsing XML Using SAX" on blackboard
	// inner class of XMLDownloadTask
	// subclass of DefaultHandler
	class AlbumHandler extends DefaultHandler {

		// declare data members
		private boolean balbumName = false;
		private boolean bartistName = false;
		private boolean blabel = false;
		private boolean bimgUrl=false;

		private String albumName;
		private String artistName;
		private String genre;
		private String imgUrl;

		//handle xml parsing
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
                        //used to read the xml
			if (qName.equalsIgnoreCase("category") && !blabel) {
				genre = attributes.getValue("label");
				blabel = true;
			}

			if (qName.equalsIgnoreCase("im:name")) {

				balbumName = true;
				albumName = "";
			}

			if (qName.equalsIgnoreCase("im:artist")) {
				bartistName = true;
				artistName = "";
			}
			
			if (qName.equalsIgnoreCase("im:image")) {
				bimgUrl = true;
				imgUrl = "";
			}

		}

		// This method may be called multiple times for a given element.
		public void characters(char ch[], int start, int length) throws SAXException {
                        //process xml info
			if (balbumName)
				albumName = albumName + new String(ch, start, length);

			if (bartistName)
				artistName = artistName + new String(ch, start, length);
			
			if (bimgUrl)
				imgUrl = imgUrl + new String(ch, start, length);
			

		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
                        //process xml end tag
			if (qName.equalsIgnoreCase("im:name"))
				balbumName = false;

			if (qName.equalsIgnoreCase("im:artist"))
				bartistName = false;
			
			if (qName.equalsIgnoreCase("im:image"))
				bimgUrl = false;
			
			if (qName.equalsIgnoreCase("entry")) {
				blabel=false;
				Album albumObj = new Album(albumName, artistName, genre, imgUrl);
				// Add the object person to a list, publish it, etc.
				albums.add(albumObj);
				publish(albumObj);
			}
		}

	}

}

