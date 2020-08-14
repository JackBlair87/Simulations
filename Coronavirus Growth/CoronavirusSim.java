import java.awt.*;
import java.util.*;
import java.text.DecimalFormat;

public class CoronavirusSim{
   private int numDay = 0;
   
   private int numSick = 0;
   
   private int numDead;
   private int numInfected;
   private int numCured;
   private int numTestedInfected;
   private double deathRate;
   private ArrayList<Person> population = new ArrayList<Person>();
   private ArrayList<Point> graph = new ArrayList<Point>();
   
   private int numRows = 53;
   private int distanceBetween = 10;
   
   private Font title = new Font("Verdana", Font.BOLD, 40);
   private Font data = new Font("Verdana", Font.BOLD, 25);
   DecimalFormat df;
   
   public CoronavirusSim(int numPeople){
      df = new DecimalFormat("0.00");
      int y = 0;
      int front = 0;
      for(int x = 0; x < numPeople; x++){
         front = (x / numRows);
         y = (x % numRows);
         population.add(new Person(front * distanceBetween + distanceBetween, 250 + (y * distanceBetween + distanceBetween)));
      }
      
      int x = (int) (Math.random() * population.size());
      population.get(x).becomeSick();      
            
      numDead = 0;
      numInfected = 1;
      numCured = 0;
      numTestedInfected = 0;
      deathRate = numDead/numInfected;
   }
   
   public boolean isFinished(){
      if(numSick == 0 && numDay != 0)
         return true;
      return false;
   }
   
   public void tick(){
      numDay++;
   
      for(Person p : population)
         if(p.isInfected() && !p.isDead() && !p.isCured()){
            int x = (int) (Math.random() * population.size());
            if(!population.get(x).isInfected()){
               numInfected++;
               population.get(x).becomeSick();
            }
         }
      
      numDead = 0;
      numTestedInfected = 0;
      numSick = 0;
      numCured = 0;
      for(Person p : population){
         p.tick();
         if(p.isDead())
            numDead++;
         if(p.testedInfected())
            numTestedInfected++;
         if(p.isInfected() && !p.isDead() && !p.isCured())
            numSick++;
         if(p.isCured())
            numCured++;
      }
      
      deathRate = (double) numDead / (double) numInfected;
      graph.add(new Point(numInfected, numDay, 5, Color.RED));
      graph.add(new Point(numSick, numDay, 5, Color.YELLOW));
      graph.add(new Point(numTestedInfected, numDay, 5, Color.ORANGE));
   }

   public void render(Graphics g){
      for(Person p : population)
         p.render(g, (int) (0.75 * distanceBetween));
         
      g.setColor(Color.WHITE);   
      g.setFont(title); 
      g.drawString("Day " + numDay, 20, 40);
      g.setFont(data);
      g.drawString("Currently Sick: " + numSick, 20, 80);
      g.drawString("Total Infected: " + numInfected, 20, 120);
      g.setColor(Color.RED);
      g.drawString("Num Dead: " + numDead, 20, 160);
      g.setColor(Color.GREEN);
      g.drawString("Num Cured: " + numCured, 20, 200);
   
      g.setColor(Color.WHITE);
      g.drawString("DeathRate: " + df.format(deathRate * 100) + "%", 20, 240);
      g.drawString("Num 'Confirmed': " + numTestedInfected, 20, 280);
      
      for(Point point : graph)
         point.render(g);
   }
   
   public void complete(){
      System.out.println("Done");
   }
}