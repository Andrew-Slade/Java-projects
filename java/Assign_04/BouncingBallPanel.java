
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


/*
* Class that handles the panel used for the ball animation
*/
public class BouncingBallPanel extends JPanel implements ActionListener {
  //animation panel object
  private AnimationPanel animator = new AnimationPanel();


  //start button for ANIMATION
  private JButton startAnim = new JButton("Start"); 

     
  // create new panel with flow layout
  private JPanel panel3 = new JPanel(new FlowLayout());

		
  //stop button for ANIMATION
  private JButton stopAnim = new JButton("Stop");


  //constructor
  public BouncingBallPanel() {
    createAndShowGUI();
  }


  //create GUI
  private void createAndShowGUI() {		
    // set layout and add components
    setLayout(new BorderLayout());

    // set layout and add components
    setLayout(new BorderLayout()); //border layout
    setPreferredSize(new Dimension(350, 350)); //panel size
    setDoubleBuffered(true); //make sure double buffered
		 

    //start button for animation
    startAnim.addActionListener(this);//add listener
    panel3.add(startAnim); //add to panel
    startAnim.setHorizontalAlignment(JButton.CENTER); //put center

    //stop button for animation
    stopAnim.addActionListener(this);//add listener
    panel3.add(stopAnim); //add to panel
    stopAnim.setHorizontalAlignment(JButton.CENTER); //put center
    stopAnim.setEnabled(false); //keep stop button disabled till needed

    add(animator, BorderLayout.CENTER); //add animation panel to panel

    add(panel3, BorderLayout.PAGE_START); //add panel 3 
  }



  //listen for actions then process
  @Override
  public void actionPerformed(ActionEvent arg0) {
    if(arg0.getActionCommand() == "Start"){
      //start button pressed
      startAnim.setEnabled(false); //disable start button
      stopAnim.setEnabled(true); //enable stop button
      animator.start(); //start animation
    }
    else if (arg0.getActionCommand() == "Stop"){
      //stop button pressed
      startAnim.setEnabled(true); //enable start button
      animator.stop(); //stop animation
      stopAnim.setEnabled(false); //disable stop button
    }

  }

	
  //accessor for dimension
  public Dimension getDim(){
    return panel3.getSize();
  }		
}
