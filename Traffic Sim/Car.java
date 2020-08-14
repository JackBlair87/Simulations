import java.awt.geom.Point2D; //2D points
import java.io.*; //screen
import java.util.*; //Math
import java.awt.*;  //Colors
import javax.imageio.ImageIO; //Image Prjection
import javax.swing.*; //Window
import java.awt.image.BufferedImage; //Image Prjection
import java.awt.geom.AffineTransform; //Image Prjection

public class Car{
   //physics
   public Point2D.Double myCoordinates;
   public Point2D.Double myVelocity;
   public double myAngle;
   public double mySteering;
   public double myAcceleration;
   public ArrayList<Car> otherCars;
   
   //constants
   private final double myLength = 100.0;
   private final double myHeight = 50.0;
   public final double maxTurning = 1;
   
   //characteristics
   public double maxAccel = 0.001;
   public double maxVelocity = 5.0;
   public double bDecel = 3.0;
   public double fDecel = 0.5;
   public String icon = "GreyCar.png";
   public BufferedImage myCar;
   
   public Car(double x, double y, double turn, Car c){
      otherCars = new ArrayList<Car>();
      otherCars.add(c);
      myCoordinates = new Point2D.Double(x, y);
      myVelocity = new Point2D.Double(0, 0);
      mySteering = 0.0;
      myAcceleration = 0.0;
      myAngle = turn;
      try{
         myCar = ImageIO.read(new File(icon));
      }catch(Exception e) { }
   }
   
   public Car(double x, double y, double turn){
      otherCars = new ArrayList<Car>();
      myCoordinates = new Point2D.Double(x, y);
      myVelocity = new Point2D.Double(0, 0);
      mySteering = 0.0;
      myAcceleration = 0.0;
      myAngle = turn;
      try{
         myCar = ImageIO.read(new File(icon));
      }catch(Exception e) { }
   }
   
   public void setStats(double maxAcc, double maxVel, double bDe, double fDe, String color){
      icon = color + "Car.png";
      try{
         myCar = ImageIO.read(new File(icon));
      }catch(Exception e) { }
      
      maxAccel = maxAcc;
      maxVelocity = maxVel;
      bDecel = bDe;
      fDecel = fDe;
   }

   public void tick(double deltaTime, String a){
      String s = "";
      if(findMinDistance(otherCars) < 200)
         s = "B";
      else
         s = "A";
   
      updateMode(s, deltaTime);
      calculateLocation(deltaTime);
      locationCheck();
   }
   
   public void render(Graphics g){
      double x = myCoordinates.getX() - (myLength/2);
      double y = myCoordinates.getY() - (myHeight/2);
      AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
      at.rotate(myAngle, myCoordinates.getX(), myCoordinates.getY());
      Graphics2D g2d = (Graphics2D) g;  
      g2d.setTransform(at);                    
      g.drawImage(myCar, (int) x, (int) y, (int) (x + myLength), (int) (y + myHeight), 0, 0, 300, 150, null);
   }
   
   public void updateMode(String s, double deltaTime){
      if(s.equals("A")){ //Accelerate
         if(myVelocity.getX() < 0)
            myAcceleration = bDecel;
         else
            myAcceleration += deltaTime;
      }
      else if(s.equals("R")){ //Reverse
         if(myVelocity.getX() > 0)
            myAcceleration = -bDecel;
         else
            myAcceleration -= deltaTime;
      }
      else if(s.equals("B")){ //Brake
         if(Math.abs(myVelocity.getX()) > deltaTime * bDecel){
            if(myVelocity.getX() < 0)
               myAcceleration = bDecel;
            else
               myAcceleration = -bDecel;
         }
         else
            myAcceleration = (myVelocity.getX() * -1) / deltaTime;
      }
      else if(s.equals("S")){ //Stop
         myAcceleration = 0.0;
         myVelocity.setLocation(0.0, 0.0);
      }
      else if(s.equals("C")){ //Coast
         if(Math.abs(myVelocity.getX()) > deltaTime * fDecel){
            if(myVelocity.getX() < 0)
               myAcceleration = fDecel;
            else
               myAcceleration = -fDecel;
         }
         else{
            if(deltaTime != 0)
               myAcceleration = -1 * myVelocity.getX() / deltaTime;
         }
      }
   }
   
   public void calculateLocation(double deltaTime){
      addVelocityX((myAcceleration*deltaTime));
      myVelocity.setLocation(Math.max(-maxVelocity, Math.min(myVelocity.getX(), maxVelocity)), myVelocity.getY());
      
      double angVelocity = 0.0;
      if(mySteering != 0.0)
         angVelocity = myVelocity.getX() / (myLength/Math.sin(Math.toRadians(mySteering)));
      
      Point2D.Double myChange = new Point2D.Double((myVelocity.getX() * Math.cos(myAngle)) - (myVelocity.getY() * Math.sin(myAngle)), (myVelocity.getY() * Math.cos(myAngle)) + (myVelocity.getX() * Math.sin(myAngle)));
      myCoordinates.setLocation((myCoordinates.getX() + myChange.getX()), (myCoordinates.getY() + myChange.getY()));
      myAngle += (Math.toDegrees(angVelocity) * deltaTime);
   }
   
   public void locationCheck(){
      if(myCoordinates.getX() > 1455)
         myCoordinates.setLocation(myCoordinates.getX()%1455 - 30, myCoordinates.getY());
      if(myCoordinates.getY() > 815)
         myCoordinates.setLocation(myCoordinates.getX(), (myCoordinates.getY()%815) -30);
      if(myCoordinates.getX() < -15)
         myCoordinates.setLocation(myCoordinates.getX() + 1485, myCoordinates.getY());
      if(myCoordinates.getY() < -15)
         myCoordinates.setLocation(myCoordinates.getX(), (myCoordinates.getY()+ 845));
   }
   
   public void addVelocityX(double x){
      myVelocity.setLocation(x + myVelocity.getX(), myVelocity.getY());
   }
   
   public void addVelocityY(double y){
      myVelocity.setLocation(myVelocity.getX(), myVelocity.getY() + y);
   }
   
   public void setAcceleration(double x){
      myAcceleration = x;
   }
   
   public double getAngle(){
      return myAngle;
   }
   
   public void setSteering(double x){
      mySteering = x;
   }

   public double getVelocityX(){
      return myVelocity.getX();
   }
   
   public double getVelocityY(){
      return myVelocity.getY();
   }
   
   public double getCordX(){
      return myCoordinates.getX();
   }
   
   public double getCordY(){
      return myCoordinates.getY();
   }
   
   public double getAcceleration(){
      return myAcceleration;
   }
   
   public double getSteering(){
      return mySteering;
   }
   
   public double findMinDistance(ArrayList<Car> vehicles){
      if(vehicles != null){ 
         double min = 10000;
         for(Car c : vehicles){
            double current = findDistance(c);
            if(current < min)
               min = current;
         }
         return min;
      }
      return -1;
   }
   
   public Car findMinCar(ArrayList<Car> vehicles){
      if(vehicles != null){ 
         double min = 10000;
         int x = 0;
         int minimum = 0;
         for(Car c : vehicles){
            double current = findDistance(c);
            if(current < min){
               min = current;
               minimum = x;
            }
            x++;
         }
         return vehicles.get(minimum);
      }
      return null;
   }

   
   //returns the distance between any car and itself
   public double findDistance(Car c){
      if(c != null){
         double x = c.getCordX();
         double y = c.getCordY();
         double a2 = Math.pow(x - myCoordinates.getX(), 2);
         double b2 = Math.pow(y - myCoordinates.getY(), 2);
         return Math.sqrt(a2 + b2);
      }
      System.out.println("Car with null object");
      return -1;
   }
   
   //adds the car to the arrayList of other cars
   public void avoidCar(Car c){
      otherCars.add(c);
   }
}