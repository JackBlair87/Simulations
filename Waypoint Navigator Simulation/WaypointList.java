import java.util.*; //For ArrayList
import java.awt.*;  //For Graphics g

public class WaypointList{
   private ArrayList<Waypoint> waypoints; 
   
   public WaypointList(){
      waypoints = new ArrayList<Waypoint>();
   }
   
   public void addWaypoint(Waypoint waypoint){
      waypoints.add(waypoints.size(), waypoint); //adds the waypoint to the ArrayList and sets the waypoint int to the waypoint it is in the array
      waypoints.get(waypoints.size()-1).setInt(waypoints.size());
   }
   
   public void tick(double deltaTime){
      for(int x = 0; x < waypoints.size(); x++) //calls the tick method for every waypoint
         waypoints.get(x).tick(deltaTime); //O(n)
   }
   
   public Waypoint getWay(int x){ //index, not numbers
      return waypoints.get(x); //O(1)
   }
   
   public int getWayLength(){ //index, not numbers
      return waypoints.size();
   }

   public void render(Graphics g){
      if(waypoints.size() > 0)
         for(int x = waypoints.size()-1; x >= 0; x--) //O(n)
            if(waypoints.get(x) != null)
               waypoints.get(x).render(g); //calls render method for every waypoint
   }
   
   public String toString(){
      String fin = "Waypoints: " + "\n";
      for(int x = 0; x < waypoints.size(); x++)
         fin += waypoints.get(x) + "\n";
      return fin;
   }
   
   public boolean willIntersect(double x, double y){
      boolean intersects = false;
      for(int n = 0; n < waypoints.size(); n++) //loops through every waypoint to see if it will intersect
         if(x > waypoints.get(n).getX()-waypoints.get(n).getR() && x < waypoints.get(n).getX()+waypoints.get(n).getR())
            if(y > waypoints.get(n).getY()-waypoints.get(n).getR() && y < waypoints.get(n).getY()+waypoints.get(n).getR())
               intersects = true;
      return intersects;
   }
}