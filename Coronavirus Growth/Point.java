import java.awt.*;
  
public class Point{
   private int myX;
   private int myY;
   private int numInfected;
   private int size;
   private int day;
   private Color c;
   
   public Point(int num, int dia, int siz, Color color){
      numInfected = num;
      day = dia;
      myX = (day*10) + 500;
      myY = ((int) (230-(numInfected*0.02)));
      size = siz;
      c = color;
   }
   
   public void render(Graphics g){
      g.setColor(c);
      g.fillOval(myX - size/2, myY - size/2, size, size);  
   }
}


