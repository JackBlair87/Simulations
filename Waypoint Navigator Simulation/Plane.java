import java.awt.*; 
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform; //to turn plane
import java.awt.geom.Point2D;

public class Plane{
   public boolean missionComplete = false; //mission complete public boolean
   
   //Physics
   private Point2D.Double myCoordinates;
   private Point2D.Double myVelocity;
   private double myAngle;
   private double mySteering;
   private double myAcceleration;
   
   //Constants
   private double myWidth = 50; //plane size
   private double myHeight = 50;
   public final double maxTurning = 0.5;
   private double maxVelocity = 4.5;
   private String icon = "PlaneIcon.png"; //image name
   private BufferedImage plane; 
   
   //Waypoints
   private WaypointList points = new WaypointList(); //personal list of waypoints
   private Waypoint nextWaypoint; //next waypoint
   private Wind wind; //adds in wind
   
   public Plane(double x, double y, double turn, WaypointList pointWays){
      try{
         plane = ImageIO.read(new File(icon));
      }catch(Exception e) { }
      myCoordinates = new Point2D.Double(x, y); //set up pyhsics
      myVelocity = new Point2D.Double(0, 0);
      mySteering = 0.0;
      myAcceleration = 0.0;
      myAngle = turn;
      
      points = pointWays; //add waypoints and wind
      nextWaypoint = points.getWay(0);
      wind = new Wind();
   }

   public Plane(){
      try{
         plane = ImageIO.read(new File(icon));
      }catch(Exception e) { }
      myCoordinates = new Point2D.Double(750, 500); //set up physics
      myVelocity = new Point2D.Double(0, 0);
      mySteering = 0.0;
      myAcceleration = 0.0;
      myAngle = Math.PI/2;
      
      nextWaypoint = points.getWay(0); //set next waypoint and wind
      wind = new Wind();
   }

   public void render(Graphics g){
      points.render(g);  //display waypoints
      //drawLineToNext(g); //(Optional) draws a line to the next waypoint
      wind.render(g);
      drawCenteredPlane(g, myCoordinates.getX(), myCoordinates.getY()); //draw plane
   }
   
   public void drawCenteredPlane(Graphics g, double x, double y){
      x -= myWidth/2; //find center of plane
      y -= myHeight/2; 
      AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
      at.rotate(myAngle, myCoordinates.getX(), myCoordinates.getY()); //rotate plane image
      Graphics2D g2d = (Graphics2D)g;  
      g2d.setTransform(at);                    
      g.drawImage(plane, (int) x, (int) y, (int) (x + myWidth), (int) (y + myHeight), 0, 0, 832, 747, null); //and draw it
   }
   
   /*Getters and setters*/
   public void setX(double x){ 
      myCoordinates.setLocation(x, myCoordinates.getY());
   }
   
   public void setY(double y){
      myCoordinates.setLocation(myCoordinates.getX(), y);
   }
   
   public double getX(){
      return myCoordinates.getX();
   }
   
   public double getY(){
      return myCoordinates.getY();
   }
   
   public Waypoint getNextWaypoint(){
      return nextWaypoint;
   }
   
   public WaypointList getWaypoints(){
      return points;
   }
      
   /*Called 30 times / sec, updates plane location and waypoint*/   
   public void tick(double deltaTime){
      myAngle = myAngle % (Math.PI*2); //makes sure angle is within -2Pi - 2Pi
      //points.tick(deltaTime); //Only for Moving Waypoint Extension
      if(myVelocity.getX() <= maxVelocity) //beginning acceleration
         myAcceleration += deltaTime * 0.1;
           
      double angle = calculateAngle(nextWaypoint); //finds the next angle and shifts it between -2Pi and 2Pi
      if(angle < myAngle - Math.PI)
         angle += Math.PI*2;
      if(angle > myAngle + Math.PI)
         angle -= Math.PI*2;
      
      if(Math.abs(myAngle - angle) < 0.08) //Straight
         mySteering = 0.0;
      else if(myAngle < angle) //Right
         mySteering = maxTurning;
      else //Left
         mySteering = -maxTurning;
            
      calculateLocation(deltaTime); //Do math to find position    
            
      wind.tick(); //Update wind and wind vector
      setX(getX() + wind.getX() * deltaTime);
      setY(getY() + wind.getY() * deltaTime);
         
      if(nextWaypoint.contains(myCoordinates)) //Waypoint check
         hitWaypoint(nextWaypoint);
   }
   
   public void findNextWaypoint(){
      if(points.getWay(nextWaypoint.getInt()) != null)
         nextWaypoint = points.getWay(nextWaypoint.getInt());
   }
   
   public void hitWaypoint(Waypoint way){ //what happens if waypoint is hit
      way.completed();
      System.out.println("Completed Waypoint " + way.getInt());
      if(way != points.getWay(points.getWayLength()-1))
         findNextWaypoint();
      else
         missionComplete = true;
   }
   
   public void drawLineToNext(Graphics g){
      g.setColor(Color.WHITE); //Draw new white line from my X and Y to the other waypoint X and Y
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(2));
      g.drawLine((int) (nextWaypoint.getX()), (int) (nextWaypoint.getY()), (int) myCoordinates.getX(), (int) myCoordinates.getY());
   }
    
   public void calculateLocation(double deltaTime){
      addVelocityX((myAcceleration*deltaTime));
      myVelocity.setLocation(Math.max(-maxVelocity, Math.min(myVelocity.getX(), maxVelocity)), myVelocity.getY());
      //adjusts velocity based on acceleration
      double angVelocity = 0.0;
      if(mySteering != 0.0)
         angVelocity = myVelocity.getX() / (myWidth/Math.sin(Math.toRadians(mySteering)));
      //changes angular velocity
      Point2D.Double myChange = new Point2D.Double((myVelocity.getX() * Math.cos(myAngle)) - (myVelocity.getY() * Math.sin(myAngle)), (myVelocity.getY() * Math.cos(myAngle)) + (myVelocity.getX() * Math.sin(myAngle)));
      myCoordinates.setLocation((myCoordinates.getX() + myChange.getX()), (myCoordinates.getY() + myChange.getY()));
      myAngle += (Math.toDegrees(angVelocity) * deltaTime);
      //updates angle and XY
   }
   
   public void addVelocityX(double x){
      myVelocity.setLocation(x + myVelocity.getX(), myVelocity.getY());
   }
   
   public void addVelocityY(double y){
      myVelocity.setLocation(myVelocity.getX(), myVelocity.getY() + y);
   }
   
   public double calculateAngle(Waypoint way){
      if(way != null) //or the difference between the car and the wall, whichever is less
         return Math.atan2(way.getY()-myCoordinates.getY(), way.getX()-myCoordinates.getX());
      return 0;
   }
}