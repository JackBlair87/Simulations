import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DPPanel extends JPanel{
   private BufferedImage mImage;
   private DoublePendulum dp; 
   
   public DPPanel(){
      dp = new DoublePendulum(720, 225); //Each Panel has a private double pendulum object, which does all of the math and physics
   }
   
   public void begin(){
      long lastLoopTime = System.nanoTime();
      final int TARGET_FPS = 40;
      final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
      double lastFpsTime = 0;  
      int fps = 0; 
   
      while(true){ //loop forever
         long now = System.nanoTime();
         long updateLength = now - lastLoopTime;
         lastLoopTime = now;
         double delta = updateLength / ((double)OPTIMAL_TIME);
      
         dp.tick(delta);
         repaint();
      
         try{
            Thread.sleep((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000); //Try to wait 
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
      g.setColor(Color.BLACK); //Make the entire screen black, then draw on the double pendulum
      g.fillRect(0, 0, getWidth(), getHeight());
      dp.render(g);
   }
}