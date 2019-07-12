
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit; 
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.*;
import java.awt.Rectangle;
import java.util.Random;

/*
* implement the animation portion here
* some repurposed from class example
*/
public class AnimationPanel extends JPanel implements Runnable{
  private ArrayList<Ball> ballArr = new ArrayList<Ball>(); //ball arraylist
  private Dimension dim = null; //dimension reference
  private volatile Thread threader = null; //thread object
  private Ball ball = null; //ball object reference
  private int initR; //initial radius
  private final int Height = 350; //height of platform
  private int initX; //initial x pos
  private int initY; //init y pos
  private int initDx; //init partial x
  private int initDy; //init partial y
  private int ballCount; //ball count


  //start animation
  public void start(){
    threader = new Thread(this);
    threader.start();
  }


  //stop animation
  public void stop(){
    threader.interrupt();
    threader = null;
  }


  //paint components overrident method
  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g); //paint 
    Random ran = new Random(); //create random object
    ballCount = ran.nextInt(300) + 10;//create up to 1000

    //if no current dimensions exist
    if(dim == null){
      //instantiate a slew of random balls
      //each will have some random values 
      //for rad,pos, partial, etc
      for(int t = 0; t < ballCount; t++){
        // make sure not on edge
        // but also anywhere
        initX = ran.nextInt(350 - initR) + initR;
        initY = ran.nextInt(350 - initR) + initR;
        initR = ran.nextInt(15) + 5;//random ball size

        initDx = ran.nextInt(10) + 5; //make parital veloc
        int ranMotX = ran.nextInt(2) + 0; //make 1/3 chance
        if(ranMotX % 2 == 0){
          //if chance is unsucessful 
          initDx = (-1) * initDx;//change initial velocity
        }
        initDy = ran.nextInt(10) + 5; //make partial veloc
        int ranMotY = ran.nextInt(2) + 0;// 1/3 chance
        if(ranMotY % 2 == 0){
          //chance is unsucessful
          initDy = (-1) * initDy;//change initial velocity
        }

        float f = ran.nextFloat(); //color temp
        float z = ran.nextFloat(); //color temp
        float a = ran.nextFloat(); //color temp
        Color ranColor = new Color(f,z,a);//new random color
        //constructor call
        ball = new Ball(ranColor, initR, initX,
                         initY, initDx, initDy);
        //add to array list for easy reference\
        ballArr.add(ball);        
      }
      //get size of panel
      dim = this.getSize();
    }

    //create a new white triangle
    g.setColor(Color.WHITE); //color it white
    Rectangle r = new Rectangle(0, 0, (int) dim.getWidth(),
                                (int) dim.getHeight());

    g.fillRect(0, 0, (int) r.getWidth(), (int) r.getHeight());    
    //done with the triangle

    //make sure movement is smooth
    for(Ball ballIt : ballArr){
      ballIt.draw(g); //draw ball
      ballIt.move(dim); //move the ball
    }
}



  //thread runner
  //from class notes
  public void run(){
    long beforeTime, timeDiff, sleep;
 
    beforeTime = System.currentTimeMillis();
    Thread thisThread = Thread.currentThread();
  
    while (threader == thisThread) {
 
      repaint();
      timeDiff = System.currentTimeMillis() - beforeTime;
      int DELAY = 30; //change this for animation speed
      sleep = DELAY - timeDiff;
      if (sleep < 0) {
        sleep = 2;
      } 
      try {
        Thread.sleep(sleep);
      }
      catch (InterruptedException e) {
        return;
      } 
      beforeTime = System.currentTimeMillis();
    }
  }
}
