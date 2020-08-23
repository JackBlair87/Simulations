import java.awt.*;  //For Graphics g
import java.util.*; //For Math
import java.awt.geom.Point2D; //For point 2D

public class Waypoint{
   public Point2D.Double myCoordinates; //X and Y Coordinates
   private int myInt; //Number in which waypoint will be completed
   private double myRadius = 100; 
   private boolean completed = false; //If the waypoint has been completed
   private Color myRingColor = new Color(51, 153, 255); //beginning blue colors
   private Color myCenterColor = new Color(0, 0, 204);
   
   public Waypoint(double x, double y, double radius){ //set X, Y, and radius
      myCoordinates = new Point2D.Double(x, y);
      myRadius = radius;
   }
   
   public Waypoint(double x, double y){ //set X and Y with default radius
      myCoordinates = new Point2D.Double(x, y);
      myInt = -1;
   }
   
   public void setX(double x){ //setters
      myCoordinates.setLocation(x, myCoordinates.getY());
   }
   
   public void setY(double y){
      myCoordinates.setLocation(myCoordinates.getX(), y);
   }
   
   public void setInt(int x){
      myInt = x;
   }

   public void setR(double r){
      myRadius = r;
   }

   public double getX(){ //getters
      return myCoordinates.getX();
   }
   
   public double getY(){
      return myCoordinates.getY();
   }
   
   public double getR(){
      return myRadius;
   }

   public int getInt(){
      return myInt;
   }
   
   public boolean isComplete(){
      return completed;
   }
   
   public void completed(){ //when the waypoint is completed
      completed = true;
      myRingColor = new Color(0, 204, 0); //change to green colors
      myCenterColor = new Color(0, 102, 0);
   }
   
   public void render(Graphics g){
      Map<?, ?> desktopHints = 
         (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

     Graphics2D g2d = (Graphics2D) g;
     
     if (desktopHints != null) {
         g2d.setRenderingHints(desktopHints);
     }
     
      g.setColor(myRingColor); //draw exterior boundary
      g.fillOval((int) (myCoordinates.getX()-(myRadius/2)), (int) (myCoordinates.getY()-(myRadius/2)), (int) myRadius, (int) myRadius);
      g.setColor(myCenterColor); //draw darker interior
      g.fillOval((int) (myCoordinates.getX()-(myRadius/6)), (int) (myCoordinates.getY()-(myRadius/6)), (int) myRadius/3, (int) myRadius/3);
      g.setColor(Color.WHITE); //draw number
      g.drawString("" + myInt, (int) myCoordinates.getX()-3, (int) myCoordinates.getY()+5);
   }
   
   public String toString(){
      String fin = myInt + ": (" + myCoordinates.getX() + ", " + myCoordinates.getY() + ") ";
      if(completed)
         fin += ", Done";
      else
         fin += ", Not Done";
      return fin;
   }
   
   public boolean contains(Point2D.Double otherCoords){ //see if the plane is inside this waypoint
      return Math.sqrt((int) Math.pow(otherCoords.getX()-myCoordinates.getX(), 2) + (int) Math.pow(otherCoords.getY()-myCoordinates.getY(), 2)) <= myRadius/2;
   }
   
   public void tick(double deltaTime){
      //do nothing
   }
}
