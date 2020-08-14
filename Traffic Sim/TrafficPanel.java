import javax.swing.*;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.*; 
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.File;

public class TrafficPanel extends JPanel{
   private BufferedImage mImage; 
   private TrafficSim ts;
   private TrafficListener trafficl;
  
   public TrafficPanel(){
      ts = new TrafficSim(trafficl);
   }
   
   public void begin(TrafficListener tl){
      trafficl = tl;
      long lastLoopTime = System.nanoTime();
      final int TARGET_FPS = 30;
      final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
      double lastFpsTime = 0;  
      int fps = 0; 
   
      while(true){
         long now = System.nanoTime();
         long updateLength = now - lastLoopTime;
         lastLoopTime = now;
         double delta = updateLength / ((double)OPTIMAL_TIME);
      
         ts.tick(delta);
         repaint();
      
         try{
            Thread.sleep((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
         }
         catch (Exception e) {
            System.out.println("Something went wrong.");
         }
      }       
   }

   @Override
   public void paintComponent(Graphics g)          //called each time the image needs to be refreshed
   { 
      draw();
      g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
   }

   public void draw(){
      mImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);     
      Graphics g = mImage.getGraphics(); 
      ts.render(g); //then renders the data
   }
   
   public TrafficSim getSim(){
      return ts;
   }
}