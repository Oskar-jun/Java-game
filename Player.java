public class Player extends Humanoid{

    private final Ship_Boosters[] weared = new Ship_Boosters[5];

    public Player(String name){
        super (name);
    }

    public Ship_Boosters[] getWeared( weared  )
        {return weared;}
}