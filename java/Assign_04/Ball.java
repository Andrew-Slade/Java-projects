
import java.awt.Dimension;
import java.lang.Math.*;
import java.awt.*;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Toolkit;


/*
* class for ball objects
*/
public class Ball{
  // declare data members
  Color c;
  int r,x,y,z,dx,dy,dz;


  // generate constructor
  public Ball(Color c, int r, int x, int y, int dx, int dy) {
    super();
    this.c = c; //color
    this.r = r; //radius
    this.x = x; //x
    this.y = y; //y
    this.dx = dx; //partial x
    this.dy = dy; //partial y
  }


  
  // accessor methods to get values of each of data members
  public Color getC(){ return c;}
  public int getR(){return r;}
  public int getX(){return x;}
  public int getY(){return y;}
  public int getDx(){return dx;}
  public int getDy(){return dy;}
  //end accessors


  //simple collision handler
  //takes the dimensions of the box in which the ball moves
  public void move(Dimension d){
    if (x <= r || x >= (int) Math.round(d.getWidth())){
      //if ball is too close to panel in x direction, move it the other way 
      dx = -dx; //change direction
    }
    else if (y <= r || y >= (int) Math.round(d.getHeight()) ){
      //if ball is too close to panel in y direction, move it the other way 
      dy = -dy; //change direction
    }
    x += dx; //move ball to new x
    y += dy; //move ball to new y
  }


  //draw ball to screen ball
  public void draw(Graphics g){
    g.setColor(c); //set color
    g.fillOval(x,y, 2*r, 2*r); //create circle
    Toolkit.getDefaultToolkit().sync(); //extra step for linux graphics buffer
  }
}
