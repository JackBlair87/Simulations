import javax.swing.*; //For Panel
import java.awt.*; //For graphics
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Scanner; //For readIntoArray from file
import java.io.File;

public class PathPanel extends JPanel{
   private BufferedImage mImage;
   private WaypointList points = new WaypointList();
   private Plane plane;
   public static BufferedImage world;
   public final String file = "Waypoints.txt";
   private Scanner scan = null;

   public PathPanel(){
      //readIntoArray(file);
      generateRandomWaypoints(10, 1500, 750);
      
      System.out.println(points);
      plane = new Plane(100, 200, 0, points); //Start out facing right
   }
   
   public void begin(){
      long lastLoopTime = System.nanoTime();
      final int TARGET_FPS = 30;
      final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
      double lastFpsTime = 0;  
      int fps = 0; 
   
      while(true){ //loop forever
         long now = System.nanoTime();
         long updateLength = now - lastLoopTime;
         lastLoopTime = now;
         double delta = updateLength / ((double)OPTIMAL_TIME);
         
         if(!plane.missionComplete){ //while all the waypoints are not hit
            plane.tick(delta); //keep going, tick and render
            repaint();  
         }
         else
            System.exit(0); //otherwise exit
      
         try{
            Thread.sleep((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000); //Try to wait 
         }
         catch (Exception e) {
            System.out.println("Something went wrong.");
         }
      }
   }
   
   @Override
   public void paintComponent(Graphics g){ 
      draw();
      g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
   }

   public void draw(){
      mImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);     
      Graphics g = mImage.getGraphics();    
      plane.render(g); //plane render handles waypoints and wind
   }
   
   /*Creates WaypointList from file*/
   public void readIntoArray(String file){
      try{ //try catch with the file
         scan = new Scanner(new File(file));  
      }
      catch(IOException e){
         System.out.println("No File??");
         System.exit(0);   
      }
      
      while(!scan.nextLine().equals("---")){
      
      }
      int num = scan.nextInt(); //num represents the number of waypoint
      scan.nextLine();
      scan.nextLine();
   
      for(int x = 0; x < num; x++){ //read the n waypoints from the file
         String line = scan.nextLine();
         String[] words = line.split(" ");
         points.addWaypoint(new Waypoint(Integer.parseInt(words[1]), Integer.parseInt(words[2])));
      }
   }
   
   /*Creates WaypointList randomly*/
   public void generateRandomWaypoints(int num, int xMax, int yMax){
      if(num <= 50)
         for(int n = 0; n < num; n++){ //for "num" number of waypoints
            int x = (int) (Math.random()*(xMax - 200) + 100);
            int y = (int) (Math.random()*(yMax - 200) + 100);
            while(points.willIntersect(x, y) || ((x < 200 && x > 0) && (y < 200 && y > 0))){ //make sure no waypoints will overlap
               x = (int) (Math.random()*(xMax - 200) + 100);
               y = (int) (Math.random()*(yMax - 200) + 100);
            }
            points.addWaypoint(new Waypoint(x, y));
         }
      else{
      System.out.println("Too many Waypoints");
      System.exit(0);
      }
   }
}
