import java.awt.event.*;      
import javax.swing.*;         
import java.awt.*;       
import java.io.*;

public class TrafficListener implements KeyListener{
   private Car car;
   String mode = "C";
   
   public TrafficListener(Car carTrack){
      car = carTrack;
   }

   @Override
   public void keyPressed(KeyEvent e){
      int key = e.getKeyCode();
      switch(key){
         case KeyEvent.VK_LEFT:
            car.mySteering = -car.maxTurning;
            break;
         case KeyEvent.VK_RIGHT:
            car.mySteering = car.maxTurning;
            break;
         case KeyEvent.VK_S:
            mode = "B"; //Brake
            break;
         case KeyEvent.VK_D:
            mode = "A"; //Accelerate
            break; 
         case KeyEvent.VK_A:
            mode = "R"; //Reverse
            break; 
      }
   }
   
   @Override
   public void keyReleased(KeyEvent e){
      int key = e.getKeyCode();
      switch(key){
         case KeyEvent.VK_LEFT:
            car.mySteering = 0.0;
            break;
         case KeyEvent.VK_RIGHT:
            car.mySteering = 0.0;
            break;
         case KeyEvent.VK_S:
            mode = "S"; //Stopped
            break;
         case KeyEvent.VK_D:
            mode = "C"; //Coast
            break;
         case KeyEvent.VK_A:
            mode = "C"; //Coast
            break;
      }
   }
   
   @Override
   public void keyTyped(KeyEvent e){
   
   }
   
   public String getMode(){
      return mode;
   }
}