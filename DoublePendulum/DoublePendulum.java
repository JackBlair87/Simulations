import java.awt.*; //For Graphics
import java.util.*; //For Math

public class DoublePendulum{
   private int myX;
   private int myY;
   private double length1 = 200;
   private double length2 = 200;
   private int weight1 = 60;
   private int weight2 = 60;

   private double theta1 = Math.PI/2;
   private double theta2 = Math.PI/2;
   
   private double x1Loc;
   private double y1Loc;
   private double x2Loc;
   private double y2Loc;
   
   private double velocity1 = 0;
   private double velocity2 = 0;
   private double accel1 = 0;
   private double accel2 = 0;
   
   //private double gravity = 9.80665;
   private double gravity = 0.5;

   public DoublePendulum(int x, int y){
      myX = x;
      myY = y;
   }
   
   public void tick(double delta){
   /*Implement the formula for angular acceleration*/
      double num1 = -gravity * (2 * weight1 + weight2) * Math.sin(theta1);
      double num2 = -weight2 * gravity * Math.sin(theta1 - 2 * theta2);
      double num3 = -2 * Math.sin(theta1 - theta2) * weight2;
      double num4 = velocity2 * velocity2 * length2 + velocity1 * velocity1 * length1 * Math.cos(theta1 - theta2);
      double den = length1 * (2 * weight1 + weight2 - weight2 * Math.cos(2 * theta1 - 2 * theta2));
      accel1 = (num1 + num2 + num3 * num4) / den;
      
      num1 = 2 * Math.sin(theta1 - theta2);
      num2 = velocity1 * velocity1 * length1 * (weight1 + weight2);
      num3 = gravity * (weight1 + weight2) * Math.cos(theta1);
      num4 = velocity2 * velocity2 * length2 * weight2 * Math.cos(theta1 - theta2);
      den = length2 * (2 * weight1 + weight2 - weight2 * Math.cos(2 * theta1 - 2 * theta2));
      accel2 = (num1 * (num2 + num3 + num4)) / den;
      
      velocity1 += accel1; //increase velocity by acceleration
      velocity2 += accel2;
      
      theta1 += (velocity1 * delta); //change angle by angular velocity
      theta2 += (velocity2 * delta);
      
      x1Loc = myX + (length1 * Math.sin(theta1)); //update X and Y based on new angles
      y1Loc = myY + (length1 * Math.cos(theta1));
      
      x2Loc = (x1Loc) + (length2 * Math.sin(theta2));
      y2Loc = (y1Loc) + (length2 * Math.cos(theta2));
   }
   
   public void render(Graphics g){
      g.setColor(Color.GRAY); //draw the lines
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(5));
      g2.drawLine(myX, myY, (int) x1Loc, (int) y1Loc);
      g2.drawLine((int) x1Loc, (int) y1Loc, (int) x2Loc, (int) y2Loc);
      
      g.setColor(Color.WHITE); //draw the stationary node
      g.fillOval(myX - 15, myY - 15, 30, 30);
      g.setColor(Color.ORANGE); //draw the first node
      g.fillOval((int) x1Loc - weight1, (int) y1Loc - weight1, weight1*2, weight1*2);
      g.setColor(Color.RED); //draw the second node
      g.fillOval((int) x2Loc - weight2, (int) y2Loc - weight2, weight2*2, weight2*2);
   }
}