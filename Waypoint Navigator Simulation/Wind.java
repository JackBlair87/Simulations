import java.awt.*; 
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public class Wind{
   private Point2D.Double myVelocity;
   private double displayX = 100;
   private double displayY = 100;
   public double windConsistency = 0.90;
   private Color darkGray = new Color(100, 100, 100);
   
   public Wind(double x, double y){
      myVelocity = new Point2D.Double(x, y);
   }
   
   public Wind(){ //creates random wind vector
      myVelocity = new Point2D.Double((Math.random() * 2 - 1) / 2, (Math.random() * 2 - 1) / 2);
   }
   
   public double getX(){ //getters
      return myVelocity.getX();
   }
   
   public double getY(){
      return myVelocity.getY();
   }
      
   public void tick(){ //change the wind a little
      double increaseX = ((Math.random() * 2) - 1) / (150 * windConsistency);
      double increaseY = ((Math.random() * 2) - 1) / (150 * windConsistency);
      myVelocity.setLocation(myVelocity.getX() + increaseX, myVelocity.getY() + increaseY);
   }

   public void render(Graphics g){ //draw wind vector and background on screen
      g.setColor(darkGray);
      g.fillOval(50, 50, 100, 100);
      g.setColor(Color.YELLOW);
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(3));
      g.drawLine(100, 100, 100 + (int) (getX() * 100), 100 + (int) (getY() * 100));
   }
}