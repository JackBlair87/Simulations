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

public class CoronavirusPanel extends JPanel{
   private BufferedImage mImage; 
   private Timer time; //make a new timer and simulation
   private CoronavirusSim cs;
   private int simSpeed = 300; //ms per day
  
   public CoronavirusPanel(int s, int numP){
      cs = new CoronavirusSim(numP);
      simSpeed = s;
   }
   
   public void begin(){
      time = new Timer(simSpeed,      
            new ActionListener(){
               public void actionPerformed(ActionEvent e){
                  if(!cs.isFinished()){ //simulate until everyone is dead or cured
                     cs.tick(); //tick and render
                     repaint(); 
                  } 
                  else{
                     cs.complete();
                     System.exit(0);
                  }
               }                               
            }
            );
      time.start();
   }

   @Override
   public void paintComponent(Graphics g){ 
      draw();
      g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
   }

   public void draw(){
      mImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);     
      Graphics g = mImage.getGraphics(); 
      cs.render(g); //then renders the data
   }
}