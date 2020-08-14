import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;
import java.awt.*; 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class RandomCar extends Car{
   
   public RandomCar(double x, double y, double turn, Car c){
      super(x, y, turn, c);
      setStats(3.0, 10.0, 6.0, 1.0, "Blue");
   }

   public RandomCar(double x, double y, double turn){
      super(x, y, turn);
      setStats(3.0, 10.0, 6.0, 1.0, "Blue");
   }
   
   public void tick(double deltaTime, String s){
      super.tick(deltaTime, s);
   }
}