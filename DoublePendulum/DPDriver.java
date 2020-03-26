import javax.swing.JFrame; //import to make a new JFrame

public class DPDriver
{
   private static DPPanel dp;
   
   public static void main(String[] args) throws InterruptedException{
      JFrame frame = new JFrame("Double Pendulum Simulation"); //Name the frame "Double Pendulum Simulation"
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Make it full screen, and let the user press the X
      dp = new DPPanel(); //add a new instance of our DPPanel class
      frame.getContentPane().add(dp);
      frame.setVisible(true); //Make it visible, then hesitate before it starts swinging
      Thread.sleep(500);
      dp.begin();
   }
}