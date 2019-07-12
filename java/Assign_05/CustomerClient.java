
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.io.*;
import java.sql.SQLException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomerClient extends JFrame implements ActionListener {


  // GUI components
  /**
  * data members
  **/
  
  //GUI members
  private JButton connectButton = new JButton("Connect");
  private JButton getAllButton = new JButton("Get All");
  private JButton addButton = new JButton("Add");
  private JButton deleteButton = new JButton("Delete");
  private JButton updateButton
                             = new JButton("Update Address");
  private JButton connect = new JButton("connect"); 
  private JButton getAll = new JButton("get all");
  private JButton add = new JButton("add");
  private JButton delete = new JButton("delete");
  private JButton update = new JButton("update");
  private JLabel name = new JLabel("Name");
  private JLabel address = new JLabel("Address");
  private JLabel ssn = new JLabel("SSN");
  private JLabel zip = new JLabel("Zip Code");
  private JLabel status = new JLabel("Client Started");
  private JTextField nameF = new JTextField("");
  private JTextField addressF = new JTextField("");
  private JTextField ssnF = new JTextField("");
  private JTextField zipF = new JTextField("");
  private JPanel fields = new JPanel();
  private JPanel buttons = new JPanel();
  private JPanel displayStatus = new JPanel();
  private JTextArea output = new JTextArea();
  //end GUI members
  
  //network members
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private static final long serialVersionUID = 1L;
  private Query myQuery;
  private String serverMsg;
  //end network members

  /**
  * Usage notes
  * s - char array for processing of ssn
  * z - char array for processing of zip
  * compare1 - comparison array for ssn
  * compare2 - comparison array for zip
  **/
  private char[] s; 
  private char[] z; 
  private char[] compare1 = {'0','1','2','3','4'
                            ,'5','6','7','8','9','-'};
  private char[] compare2 = {'0','1','2','3','4'
                             ,'5','6','7','8','9'};

  private JFrame n = new JFrame(); //for option panes


   /**
   * main 
   * sends the frame to the event thread
   **/
   public static void main(String[] args) throws Exception {
    CustomerClient myClient = new CustomerClient(); 
  }


  /**
  * constructor
  **/
  private CustomerClient() {
      super("Customer Database");
      createAndShowGUI();
  }


  /**
  * Create Graphics User Interface
  **/
  private void createAndShowGUI(){
    //default info
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100,100,1050,500);
 
    /*
      the user interface panel
    */
    JPanel userPanel = new JPanel();
    userPanel.setLayout(new BorderLayout()); 
    userPanel.setBorder(new EmptyBorder(5,5,5,5)); 
    userPanel.setBackground(Color.lightGray);//color main pane
    fields.setLayout(new GridLayout(2,4,10,10)); //grid  2x4
    buttons.setLayout(new FlowLayout()); //flow layout
    fields.setBackground(Color.lightGray);//color the pane
    fields.add(name,1,0); //add name label
    fields.add(nameF,1,1); //add name field
    fields.add(address,1,2);//add address label
    fields.add(addressF,1,3);//add address field
    fields.add(ssn,2,0); //add ssn label
    fields.add(ssnF,2,1); //add ssn field
    fields.add(zip,2,2); //add zip label
    fields.add(zipF,2,3); //add zip field
    userPanel.add(fields, BorderLayout.PAGE_START); //add pane

    connect.addActionListener(this);//add action listener
    getAll.setEnabled(false); //disable button
    getAll.addActionListener(this);//add action listener
    add.setEnabled(false); //disable button
    add.addActionListener(this);//add action listener
    delete.setEnabled(false); //disable button
    delete.addActionListener(this);//add action listener
    update.setEnabled(false); //disable button
    update.addActionListener(this);//add action listener

    buttons.setLayout(new FlowLayout()); //buttons flow layout
    buttons.add(connect); //add connect button
    buttons.add(getAll); //add getAll button
    buttons.add(add); //add add button
    buttons.add(delete); //add delete button
    buttons.add(update); //add update button
    buttons.setBackground(Color.lightGray);//color the pane
    userPanel.add(buttons, BorderLayout.CENTER); //add panel

    //add label to page end of panel
    displayStatus.setLayout(new BorderLayout());
    displayStatus.add(status, BorderLayout.WEST); 
    displayStatus.setBackground(Color.lightGray);//color
    userPanel.add(displayStatus, BorderLayout.PAGE_END); //add
 
    /*
    * the output panel
    */
    JPanel outputPanel = new JPanel();
    outputPanel.setBackground(Color.darkGray);
    outputPanel.setBorder(new EmptyBorder(5,5,5,5)); 
    outputPanel.setLayout(new BorderLayout());
    output.setColumns(80); //80 columns
    output.setRows(10); //10 rows
    output.setEditable(false); //make non-editable
    JScrollPane outputArea = new JScrollPane(output);  
    outputArea.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                                         );//scroll bar

    outputPanel.add(outputArea); //add the text pane


    /*
      pack the frame
    */
    add(userPanel, BorderLayout.CENTER); //add element
    add(outputPanel, BorderLayout.PAGE_END);//add element
    pack(); //pack elements into frame
    setVisible(true); //set frame as visible
  }
        

  @Override
  /**
  * action performed override
  **/
  public void actionPerformed(ActionEvent e) {
      //sort through actions performed
      if (e.getActionCommand().equals("connect")) {
          connect();
      } else if (e.getActionCommand().equals("disconnect")) {
          disconnect();
      } else if (e.getSource() == getAll) {
          handleGetAll(); 
      } else if (e.getSource() == add) {
          handleAdd();
      } else if (e.getSource() == update) {
          handleUpdate();
      } else if (e.getSource() == delete) {
          handleDelete();
      }
  }


  /**
  * establish connection
  **/ 
  private void connect() {
    String request; //holds server requests
    try {
      // Replace 127.0.0.1 with your desired server Ip
      // Replace 9751 with your port number
      //connection
      socket = new Socket("127.0.0.1", 9751); // for my server
      System.out.println("LOG: Socket opened");

      //streams
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());

      System.out.println("LOG: Streams opened"); //log message

      connect.setText("disconnect");//change button text
      serverMsg = "connected";
           
      // Enable buttons
      getAll.setEnabled(true);
      add.setEnabled(true);
      delete.setEnabled(true);
      update.setEnabled(true);
   
      //change JLable
      status.setText(serverMsg); // change label

      } catch (UnknownHostException e) {
          System.err.println("Exception resolving host name: " + e);
      } catch (IOException e) {
          System.err.println("Exception establishing socket connection: " + e);
      }
  }

  
  /**
  * disconnect
  **/
  private void disconnect() {
    System.out.println("LOG: Socket closed");//log message
    connect.setText("connect");//set button text 
        
    // Disable buttons
    getAll.setEnabled(true);
    add.setEnabled(true);
    delete.setEnabled(true);
    update.setEnabled(true);
  
    serverMsg = "disconnected";
    //change JLable
    status.setText(serverMsg); // change label
    
    try {
        socket.close();
    } catch (IOException e) {
        System.err.println("Exception closing socket: " + e);
    }
  }


  /**
  * method to validate input for add
  **/
  private boolean validateInput(){
   
    //variable block
    boolean flag1 = false;//ssn flag
    boolean flag2 = false;//zip flag
    boolean flag3 = false;//address flag
    boolean flag4 = false;//name flag
    boolean isS = false;//is an ssn
    boolean returned = false;//return value for function
 
    String uName = nameF.getText();//user name
    String uAddr = addressF.getText();//user address
    String uSsn = ssnF.getText();//user ssn
    String uZip = zipF.getText();//user zip code

    int r; //holds results of binary search to validate ssn
    int q;//holds results of binary search to validate zip
    int count = 0;
    //end variable block
   
    if(uName.length() < 21 && uName.length() > 0){
      //if name isn't too long
      flag4 = true;
    } //end name length check
    else{
      JOptionPane.showMessageDialog(n, "not a name");
      flag4 = false;
    }
    
    if(uSsn.length() == 11){
      //if ssn isnt too long
      s = uSsn.toCharArray();
      if(s[3] == '-' && s[6] == '-'){
        //check that the '-' s are in place
        isS = true;;
      }   // end of check for - 's
      else{
        isS = false;
      }
      if(isS){
        Arrays.sort(s);
        Arrays.sort(compare1);
        for(char j : s){
        /*
          search designated array (compare1)
           with each of ssn's values
          looking only for values which are numeric or '-'
        */
          count += 1;
          if(count > 2 && j == '-'){
            flag1 = false; 
            break;
          }
          r = Arrays.binarySearch(compare1, j);
          if(r > 0){
            flag1 = true;
          } //end of binary search 
          else{
            flag1 = false; 
          }
        } //end to for loop
      }//end ssn number check
      if(!flag1){
        JOptionPane.showMessageDialog(n, "not a ssn");
      }
    } // end of ssnlength
    else{
      JOptionPane.showMessageDialog(n, "not a ssn");
      flag1 = false;
    }



    if(uAddr.length() < 41 && uAddr.length() > 0){
      //if address isnt too long
      flag3 = true;
    } // end of address lenth check
    else{
      JOptionPane.showMessageDialog(n, "not an Address");
      flag3 = false;
    }


    if(uZip.length() == 5){
      //if zip is long enough           
      z = uZip.toCharArray();
      Arrays.sort(z);
      Arrays.sort(compare2);
      for( char i : z){
        /*
          search designated array (compare2)
          with each of zip's values
          looking only for values which are numeric
        */
        q = Arrays.binarySearch(compare2, i);
        if(q >= 0){
          //if actually is a zip
          flag2 = true;
        } // end of number search through zip code
        else{
          flag2 = false;
          JOptionPane.showMessageDialog(n, "not a zip");
          System.out.println("system failure");
          break;
        }
       }//end of for loop
    } // end of zip length check
    else{
      JOptionPane.showMessageDialog(n, "not a zip");
      flag2 = false;
    }


    if(flag1 == true && flag2 == true
             && flag3 == true && flag4 == true){
      //if input is okay to submit
      returned = true;
    }
    else{
      //else return false
      returned = false;
    }
   return returned;
  }


  /**
  * handle validation for update
  **/
  private boolean validateInputUpdate(){
    //variable block
    boolean flag = false;
    boolean flag1 = false;//ssn flag
    boolean isS = false;//is an ssn
    boolean returned = false;//return value for function
 
    String uAddr = addressF.getText();//user address
    String uSsn = ssnF.getText();//user ssn

    int r; //holds results of binary search to validate ssn
    //end variable block


    if(uAddr.length() < 41 && uAddr.length() > 0){
      //if address isnt too long
      flag = true;
    } // end of address lenth check
    else{
      JOptionPane.showMessageDialog(n, "not an Address");
      flag = false;
    }

    if(uSsn.length() == 11){
      //if ssn isnt too long
      s = uSsn.toCharArray();
      if(s[3] == '-' && s[6] == '-'){
        //check that the '-' s are in place
        isS = true;;
      }   // end of check for - 's
      else{
        isS = false;
      }
      if(isS){
        Arrays.sort(s);
        Arrays.sort(compare1);
        for(char j : s){
        /*
          search designated array (compare1)
           with each of ssn's values
          looking only for values which are numeric or '-'
        */
          r = Arrays.binarySearch(compare1, j);
          if(r > 0){
            flag1 = true;
          } //end of binary search 
          else{
            flag1 = false; 
          }
        } //end to for loop
      }//end ssn number check
      if(!flag1){
        JOptionPane.showMessageDialog(n, "not a ssn");
      }
    } // end of ssnlength
    else{
      JOptionPane.showMessageDialog(n, "not a ssn");
    }
 
    if(flag && flag1){
      returned = true;
    }
    else{
      returned = false;
    }
   return returned; 
  }
 
  /**
  * handle validation for delete
  **/
  private boolean validateInputDelete(){
    //variable block
    boolean flag1 = false;//ssn flag
    boolean isS = false;//is an ssn
    boolean returned = false;//return value for function
 
    String uSsn = ssnF.getText();//user ssn

    int r; //holds results of binary search to validate ssn
    //end variable block


    if(uSsn.length() == 11){
      //if ssn isnt too long
      s = uSsn.toCharArray();
      if(s[3] == '-' && s[6] == '-'){
        //check that the '-' s are in place
        isS = true;;
      }   // end of check for - 's
      else{
        isS = false;
      }
      if(isS){
        Arrays.sort(s);
        Arrays.sort(compare1);
        for(char j : s){
        /*
          search designated array (compare1)
           with each of ssn's values
          looking only for values which are numeric or '-'
        */
          r = Arrays.binarySearch(compare1, j);
          if(r > 0){
            flag1 = true;
          } //end of binary search 
          else{
            flag1 = false; 
          }
        } //end to for loop
      }//end ssn number check
      if(!flag1){
        JOptionPane.showMessageDialog(n, "not a ssn");
      }
    } // end of ssnlength
    else{
      JOptionPane.showMessageDialog(n, "not a ssn");
    }
 
    if(flag1){
      returned = true;
    }
    else{
      returned = false;
    }
   return returned; 
  }
  

  

  /**
  * handle get all command
  **/
  private void handleGetAll() {
    output.setText(null); //clear out text area
    //send sql query
    String queryT = "GETALL";
    String Name = nameF.getText();//user name
    String Addr = addressF.getText();//user address
    String Ssn = ssnF.getText();//user ssn
    String Zip = zipF.getText();//user zip code
    myQuery = new Query(queryT, Ssn, Name, Zip, Addr); 
    //write queries to output stream
    try{
      out.writeObject(myQuery); //write queue to stream
    }
    catch (IOException e) {
      System.err.println("IOException: " + e);
    }
    try{
      Query rQuery
            = (Query) in.readObject();//read in object
      String[] temp = rQuery.getResults();//temp variable
      for(int i = 0; i < temp.length; i++){
        output.append(temp[i]);//push records to text area
        if(i % 4 == 3){
          output.append("\n");//if new record, new line
        }
        if(i % 4 != 3){
          output.append(", ");//split columns
        }
      }
     serverMsg = "Recieved: " + temp.length / 4 + " records";
     status.setText(serverMsg); // change label
    }
    catch (IOException e) {
      System.err.println("IOException: " + e);
    }
    catch (ClassNotFoundException e){
      System.err.println("ClassNotFoundException" + e);
    }
  }

  
  /**
  * handle add command
  **/
  private void handleAdd() {
    if(validateInput()){
      output.setText(null); //clear out text area
      //if input is valid
      //send sql query
      String queryT = "ADD";
      String Name = nameF.getText();//user name
      String Addr = addressF.getText();//user address
      String Ssn = ssnF.getText();//user ssn
      String Zip = zipF.getText();//user zip code
      myQuery = new Query(queryT, Ssn, Name, Zip, Addr); 
      //write queries to output stream
      try{
        out.writeObject(myQuery); //write queue to stream
      }
      catch (IOException e) {
        System.err.println("IOException: " + e);
      }
      try{
        //read in object
        Query rQuery
              = (Query) in.readObject();
        String temp = rQuery.getSuccess();
        output.append(temp);//append status to text area
        output.append("\n"); //newline
        serverMsg = temp;
        status.setText(serverMsg); // change label
      }
      catch (IOException e) {
        System.err.println("IOException: " + e);
      }
      catch (ClassNotFoundException e){
        System.err.println("ClassNotFoundException" + e);
      }
    }
    else{
    }
    
  }


  /**
  * handle delete command
  **/
  private void handleDelete() {
    if(validateInputDelete()){
      output.setText(null); //clear out text area
      //if input is valid
      //send sql query
      String queryT = "DELETE";
      String Name = nameF.getText();//user name
      String Addr = addressF.getText();//user address
      String Ssn = ssnF.getText();//user ssn
      String Zip = zipF.getText();//user zip code
      myQuery = new Query(queryT, Ssn, Name, Zip, Addr); 
      //write queries to output stream
      try{
        out.writeObject(myQuery); //write queue to stream
      }
      catch (IOException e) {
        System.err.println("IOException: " + e);
      }
      try{
        //read in object
        Query rQuery
              = (Query) in.readObject();
        String temp = rQuery.getSuccess();
        output.append(temp);//append status to text area
        output.append("\n"); //newline
        serverMsg = temp;
        status.setText(serverMsg); // change label
      } 
      catch (IOException e) {
        System.err.println("IOException: " + e);
      }
      catch (ClassNotFoundException e){
        System.err.println("ClassNotFoundException" + e);
      }
    }
  }


  /**
  * handle update command
  **/
  private void handleUpdate() {
    if(validateInputUpdate()){
      output.setText(null); //clear out text area
      //if input is valid
      //send sql query
      String queryT = "UPDATE";
      String Name = nameF.getText();//user name
      String Addr = addressF.getText();//user address
      String Ssn = ssnF.getText();//user ssn
      String Zip = zipF.getText();//user zip code
      myQuery = new Query(queryT, Ssn, Name, Zip, Addr); 
      //write queries to output stream
      try{
        out.writeObject(myQuery); //write queue to stream
      }
      catch (IOException e) {
        System.err.println("IOException: " + e);
      }
      try{
        //read in object
        Query rQuery
              = (Query) in.readObject();
        String temp = rQuery.getSuccess();
        output.append(temp);//append status to text area
        output.append("\n"); //newline
        serverMsg = temp;
        status.setText(serverMsg); // change label
      } 
      catch (IOException e) {
        System.err.println("IOException: " + e);
      }
      catch (ClassNotFoundException e){
        System.err.println("ClassNotFoundException" + e);
      }
    }
  }
              
}
