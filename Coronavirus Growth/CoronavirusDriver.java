import javax.swing.JFrame;

public class CoronavirusDriver{
   private static CoronavirusPanel cp;
   private static final int simSpeed = 300;
   private static final int numPeople = 10000;
   
   public static void main(String[] args){
      JFrame frame = new JFrame("Coronavirus Growth Simulation");
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      cp = new CoronavirusPanel(simSpeed, numPeople);
      frame.getContentPane().add(cp);
      frame.setVisible(true);
      cp.begin();
   }
}