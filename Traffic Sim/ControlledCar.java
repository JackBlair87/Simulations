import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;
import java.awt.*; 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class ControlledCar extends Car{
   
   public ControlledCar(double x, double y, double turn, Car c){
      super(x, y, turn, c);
      setStats(0.02, 5.0, 4.0, 0.5, "Red");
   }

   public ControlledCar(double x, double y, double turn){
      super(x, y, turn);
      setStats(0.02, 5.0, 4.0, 0.5, "Red");
   }
   
   public void tick(double deltaTime, String s){
      updateMode(s, deltaTime);
      calculateLocation(deltaTime);
      locationCheck();
   }
}