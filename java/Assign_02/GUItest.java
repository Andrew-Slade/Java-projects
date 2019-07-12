/*
* A file to test gui interface on my
* nix device
*
*
*
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GUItest implements Runnable{
  public void run(){
    //create window first
    JFrame nFrame = new JFrame("Hello, world!"); // new frame
    JPanel RootPanel = new JPanel(new CardLayout());

    nFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close action
    nFrame.setLocationByPlatform(true); // my preferred method for locale
    nFrame.setPreferredSize(new Dimension(750,350));



    nFrame.getContentPane().add(RootPanel);



    nFrame.pack(); //make sure content doesnt overlap
    nFrame.setVisible(true); //set to visible
    nFrame.setLayout(new FlowLayout()); //set layout
  }
  public static void main(String[] args){
    GUItest gui = new GUItest(); // instantiate guid
    SwingUtilities.invokeLater(new GUItest()); //set to run
  }
}
