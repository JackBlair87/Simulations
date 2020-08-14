import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;
import java.awt.*; 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class FollowCar extends Car{

   public FollowCar(double x, double y, double turn, Car c){
      super(x, y, turn, c);
      setStats(3.0, 10.1, 6.0, 0.5, "Green");
   }

   public FollowCar(double x, double y, double turn){
      super(x, y, turn);
      setStats(3.0, 10.1, 6.0, 0.5, "Green");
   }
   
   public void tick(double deltaTime, String s){
      double angle = calculateAngle();
      myAngle = myAngle % (Math.PI*2); //makes sure angle is within -2Pi - 2Pi
      
      if(angle < myAngle - Math.PI)
         angle += Math.PI*2;
      if(angle > myAngle + Math.PI)
         angle -= Math.PI*2;
      
      if(Math.abs(myAngle - angle) < 0.03)
         mySteering = 0.0;
      else if(myAngle < angle)
         mySteering = maxTurning;
      else
         mySteering = -maxTurning;
      
      if(findDistance(otherCars.get(0)) < 150)
         updateMode("B", deltaTime);
      else
         updateMode("A", deltaTime);
      
      calculateLocation(deltaTime);
      locationCheck();
   }
   
   public double calculateAngle(){
      if(otherCars != null)
         if(otherCars.get(0) != null) //or the difference between the car and the wall, whichever is less
            return Math.atan2(otherCars.get(0).getCordY()-myCoordinates.getY(), otherCars.get(0).getCordX()-myCoordinates.getX());
      return 0;
   }
   
   public void followCar(Car c){
      otherCars.add(0, c);
   }
}