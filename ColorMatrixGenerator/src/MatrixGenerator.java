import javax.imageio.ImageIO;
import javax.swing.JFrame; //For the JFrame
import javax.swing.UIManager;
import java.awt.Taskbar;
import java.awt.*;
import java.awt.image.BufferedImage;
//java -jar ColorMatrixGenerator.jar
//java -jar ColorMatrixGenerator.jar -NSRequiresAquaSystemAppearance False
import java.io.File;

public class MatrixGenerator{
   private static MatrixPanel screen;
   private static BufferedImage icon;
   
   public static void main(String[] args){

      UIManager.put( "control", new Color( 128, 128, 128) );
      UIManager.put( "info", new Color(128,128,128) );
      UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
      UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
      UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
      UIManager.put( "nimbusFocus", new Color(115,164,209) );
      UIManager.put( "nimbusGreen", new Color(176,179,50) );
      UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
      UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
      UIManager.put( "nimbusOrange", new Color(191,98,4) );
      UIManager.put( "nimbusRed", new Color(169,46,34) );
      UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
      UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
      UIManager.put( "text", new Color( 230, 230, 230) );
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (InstantiationException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (javax.swing.UnsupportedLookAndFeelException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      try{
         icon = ImageIO.read(new File("./lib/ColorMatrix.icns"));
         final Taskbar taskbar = Taskbar.getTaskbar();
         taskbar.setIconImage(icon);
     } catch (final UnsupportedOperationException e) {
      System.out.println("The os does not support: 'taskbar.setIconImage'");
      } catch (final SecurityException e) {
         System.out.println("There was a security exception for: 'taskbar.setIconImage'");
      }
      catch(Exception e) { 
         System.out.println(e);
      }

      JFrame frame = new JFrame("Color Matrix Generator");
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //Full screen
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      screen = new MatrixPanel();
      frame.getContentPane().add(screen);
      frame.setVisible(true);
   }
}