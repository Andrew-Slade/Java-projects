
import java.util.ArrayList;
import java.io.IOException;
import java.sql.SQLException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.io.*;

public class CustomerServer extends Thread {


  //data members
  private ServerSocket listenSocket;


  /**
  * main method
  **/
  public static void main(String args[]) {
    new CustomerServer(); //constructor
  }


  /**
  * customer server method
  * create socket with the port
  * as an argument
  **/
  private CustomerServer() {
    // Replace 9751 with your port number
    int port = 9751;
    try {
      listenSocket = new ServerSocket(port);
    }
    catch (IOException e) {
        System.err.println("Exception creating server socket: " + e);
        System.exit(1);
    }

    System.out.println("LOG: Server listening on port " + port);
    this.start();
  }


  /**
  * run()
  * The body of the server thread.
  * Loops forever, listening for and
  * accepting connections from clients.
  * For each connection, create a
  * new Conversation object to handle the
  * communication through the
  * new Socket.
  **/
  public void run() {
    try {
      while (true) {
        Socket clientSocket = listenSocket.accept();

        System.out.println("LOG: Client connected");

        /* Create a Conversation object to
         * handle this client and pass
         * it the Socket to use. 
         * If needed, we could save the Conversation
         * object reference in an ArrayList.
         * In this way we could later iterate
         * through this list looking for
         * "dead" connections and reclaim
         * any resources.
         */
         new Conversation(clientSocket);
      }
    }
    catch (IOException e) {
      System.err.println("Exception listening for connections: " + e);
    }
  }
}


/**
* The Conversation class handles
* all communication with a client.
**/
class Conversation extends Thread {


  //data members
  private Socket clientSocket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  //my database
  //replace //localhost/test with your database
  private static final String URL
                 = "jdbc:mysql://localhost/test";


  //statements
  private Statement getAllStatement = null;
  private PreparedStatement addStatement = null;
  private PreparedStatement deleteStatement = null;
  private PreparedStatement updateStatement = null;
  private ResultSet results = null;

  /**
  * Constructor
  *
  * Initialize the streams and start the thread.
  */
  Conversation(Socket socket) {
    clientSocket = socket;

    try {
      out = new ObjectOutputStream(clientSocket.getOutputStream());
      in = new ObjectInputStream(clientSocket.getInputStream());

      System.out.println("LOG: Streams opened");
    }
    catch (IOException e) {
      try {
        clientSocket.close();
      }
      catch (IOException e2) {
        System.err.println("Exception closing client socket: " + e2);
      }

     System.err.println("Exception getting socket streams: " + e);
     return;
    }

    try {
      System.out.println("LOG: Trying to create database connection");
      //open connection with pass and username
            
      secrets userInfo = new secrets(); //holds pass
      //secrets has two public Strings
      // user and password
      Connection connection = DriverManager.getConnection(URL, userInfo.user, userInfo.password);
      
      /*
        Statements and PreparedStatements here
      */
      String pQuery = "";
      getAllStatement = connection.createStatement();
      pQuery = "Insert into customer (ssn, name, address, zipCode) values (?, ?, ?, ?)";
      addStatement = connection.prepareStatement(pQuery);
      pQuery = "delete from customer where ssn=?";
      deleteStatement = connection.prepareStatement(pQuery);
      pQuery = "update customer set address = ? where ssn = ?";
      updateStatement = connection.prepareStatement(pQuery);
      //sql statements prepared

      System.out.println("LOG: Connected to database");

    }
    catch (SQLException e) {
      System.err.println("Exception connecting to database manager: " + e);
     return;
    }

    // Start the run loop.
    System.out.println("LOG: Connection achieved, starting run loop");
   this.start();
  }

  /**
  * run()
  *
  * Reads and processes input from the
  * client until the client disconnects.
  **/
  public void run() {
    System.out.println("LOG: Thread running");
    try{
      while(true){
        //recieve client queries
        try{
          Query sQuery
                = (Query) in.readObject();
          String types = sQuery.getType();
          int cases = 4;
          //process queries
          if(types.equals("GETALL")){
            handleGetAll(sQuery);
          }
          else if(types.equals("ADD")){
            handleAdd(sQuery);
          }
          else if(types.equals("UPDATE")){
            handleUpdate(sQuery);
          }
          else if(types.equals("DELETE")){
            handleDelete(sQuery);
          }
        }
        catch(EOFException e){
          in.close();
          break;
        }
      }
    }
    catch (IOException e) {
      System.err.println("IOException: " + e);
      System.out.println("LOG: Client disconnected");
    }
    catch (ClassNotFoundException e){
      System.err.println("ClassNotFoundException" + e);
    }
    finally {
      try {
        clientSocket.close();
      }
      catch (IOException e) {
        System.err.println("Exception closing client socket: " + e);
      }
    }
  }


  
  /**
  * Handler methods for executing statments
  * and returning reults
  **/
  private void handleGetAll(Query uQuery) {
    //getall execution
    String query = "select * from customer";
    ArrayList<String> ar = new ArrayList<String>();//temp
    String s1, s2, s3, s4; //temps
    String[] holder; //temp
    try{
      results =  getAllStatement.executeQuery(query);//result 
      while(results.next()){
        //push results to array list
        s1 = results.getString("name");
        s2 = results.getString("ssn"); 
        s3 = results.getString("address");
        s4 = results.getString("zipcode");
        ar.add(s1);
        ar.add(s2);
        ar.add(s3);
        ar.add(s4);
      }
      //convert array list to array
      holder = ar.toArray(new String[ar.size()]);
      uQuery.setResults(holder);//send back array
    }
    catch(SQLException e){
      System.out.println("sqlexception" + e); 
    }
    //end of getall execution
    try{
      //write new object to out stream
      out.writeObject(uQuery); //write queue to stream
    }
    catch (IOException e) {
      System.err.println("ioexception: " + e);
    }
  }


  private void handleAdd(Query uQuery) {
    //add execution
    try{
      //prep statement
      addStatement.setString(1,uQuery.getSsn());
      addStatement.setString(2,uQuery.getName());
      addStatement.setString(3,uQuery.getAddress());
      addStatement.setString(4,uQuery.getZip());
    }
    catch(SQLException e){
      uQuery.setSuccess("failure");
    }
    try{
      addStatement.executeQuery();// execute add statement
      uQuery.setSuccess("success");// send back results
    }
    catch(SQLException e){
      uQuery.setSuccess("failure");
    }
    //end add execution
    try{
      //write new object to out stream
      out.writeObject(uQuery); //write queue to stream
    }
    catch (IOException e) {
      System.err.println("ioexception: " + e);
    }
  } 


  private void handleDelete(Query uQuery) {
    //delete execution
    try{
     //prep statement
     deleteStatement.setString(1,uQuery.getSsn());
    }
    catch(SQLException e){
      uQuery.setSuccess("failure");
    }
    try{
      int n = deleteStatement.executeUpdate();//delete from db
      if(n == 0){
        //if no rows affected -> failure
        uQuery.setSuccess("failure");
      }
      else if(n == 1){
        //if affected right number of rows
        uQuery.setSuccess("success");//send success
      }
      else{
        //else failure
        uQuery.setSuccess("failure");
      }
    }
    catch(SQLException e){
      uQuery.setSuccess("failure");
    }
    //end delete execution
    try{
      //write new object to out stream
      out.writeObject(uQuery); //write queue to stream
    }
    catch (IOException e) {
      System.err.println("ioexception: " + e);
    }
  }


  private void handleUpdate(Query uQuery) {
    //update execution
    try{
     //prep statement
     updateStatement.setString(1,uQuery.getAddress());
     updateStatement.setString(2,uQuery.getSsn());
    }
    catch(SQLException e){
      uQuery.setSuccess("failure");
    }
    try{
      int n = updateStatement.executeUpdate();//delete from db
      if(n == 0){
        //if no rows affected
        uQuery.setSuccess("failure");//set failure
      }
      else if(n == 1){
        //if right number of rows affected
        uQuery.setSuccess("success");//set success
      }
      else{
        //else
        uQuery.setSuccess("failure");//set failure
      }
    }
    catch(SQLException e){
      uQuery.setSuccess("failure");
      System.out.println("failure to execute statement");
    }
    //end update execution
    try{
      //write new object to out stream
      out.writeObject(uQuery); //write queue to stream
    }
    catch (IOException e) {
      System.err.println("ioexception: " + e);
    }
  }
}
