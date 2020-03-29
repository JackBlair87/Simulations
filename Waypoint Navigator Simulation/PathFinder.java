import javax.swing.JFrame; //For the JFrame

public class PathFinder{
   private static PathPanel path;
   
   public static void main(String[] args) throws InterruptedException{
      JFrame frame = new JFrame("UAV Waypoint Navigator Simulation");
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //Full screen
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      path = new PathPanel();
      frame.getContentPane().add(path);
      frame.setVisible(true);
      Thread.sleep(500); //Wait for Frame to open
      path.begin();
   }
}
