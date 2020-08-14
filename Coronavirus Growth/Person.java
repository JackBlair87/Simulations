import java.awt.*;
  
public class Person{
   private boolean isInfected;
   private boolean isDead;
   private boolean isCured;
   private boolean showsInfected;
   private boolean foundInfected;
   private int myX;
   private int myY;

   public Person(int x, int y){
      isInfected = false;
      isDead = false;
      isCured = false;
      showsInfected = false;
      myX = x;
      myY = y;
   }

   public void tick(){ //need to factor age into it
      if(!isDead && !isCured){
         if(isInfected){
            if(showsInfected){
               if(Math.random() >= 0.985){ //death with shown infected
                  isDead = true;
                  foundInfected = true;
               }
               
               if(Math.random() >= 0.8) //gets test
                  foundInfected = true;
                  
               if(!isDead)
                  if(Math.random() >= 0.9) //cured with shown infected
                     isCured = true;
            }
            else{
               if(Math.random() >= 0.999){ //death without shown infected
                  isDead = true;
                  foundInfected = true;
               }
                  
               if(!isDead)
                  if(Math.random() >= 0.7) //cured with shown infected
                     isCured = true;
            }
         }
      }
   }
   
   public void becomeSick(){
      if(!isInfected){ //if not already sick
         isInfected = true;
         if(Math.random() >= 0.8)
            showsInfected = true;
      }
   }
   
   public void render(Graphics g, int size){
      if(isDead)
         g.setColor(Color.RED); //Dead Person
      else if(isCured)
         g.setColor(Color.GREEN); //Cured Person
      else if(showsInfected)
         g.setColor(Color.ORANGE); //Visibly Infected Person
      else if(isInfected)
         g.setColor(Color.YELLOW); //Infected Person
      else
         g.setColor(Color.GRAY); //Uninfected Person
      
      g.fillOval(myX - (size/2), myY - (size/2), size, size);
   }
   
   public boolean isInfected(){
      return isInfected;
   }
   
   public boolean isDone(){
      return isDead || isCured;
   }
   
   public boolean isDead(){
      return isDead;
   }
   
   public boolean isCured(){
      return isCured;
   }
   
   public boolean testedInfected(){
      return foundInfected;
   }
}