import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.*; 
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.File;
import java.awt.Graphics;

import java.util.*;

public class TrafficSim{
   public Car car;
   private TrafficListener tl;
   private int numberOfCars = 10; //default is 10
   private ArrayList<Car> cars;

   public TrafficSim(TrafficListener t){
      car = new ControlledCar(350, 250, Math.PI);
      tl = t;
      cars = new ArrayList<Car>();
      cars.add(car);
      for(int x = 1; x <= numberOfCars; x++)
         cars.add(new FollowCar(Math.random() * 1000, Math.random() * 1000, Math.random() * 100, (Car) cars.get(x-1)));
         
      for(Car c : cars){
         for(Car v : cars){
            if(c != v)
               c.avoidCar(v);
         }
      }
      //car.followCar(cars.get(numberOfCars));
   }
   
   public void tick(double deltaT){
      for(Car vehcile : cars){
         vehcile.tick(deltaT, tl.getMode());
      }
      car.tick(deltaT, tl.getMode());
         
      for(Car vehicle : cars){
         vehicle.tick(deltaT, "");
      }
   }
   
   public void render(Graphics g){
      for(Car vehcile : cars)
         vehcile.render(g);
      car.render(g);
   }
   
   public Car getCar(){
      return car;
   }
   
   public void setTL(TrafficListener t){
      tl = t;
   }
}