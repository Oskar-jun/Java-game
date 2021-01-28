public class Humanoid extends Object{
    private final String name;
    private int health;
    private int currentPosition=0;
    private boolean isDead=false;
    private final Item[] inventory = new Item[4];


    public Humanoid(String name) {
        this.name = name;
        this.health = 100;
    }

    public String getName(){
        return this.name;
    }

    public void healthincrease(int amount){
        if ((100)>(this.health+amount)){
            this.health+=amount;
            System.out.println ("Current health is "+ this.health);
        }else{
            this.health=100;
        }
    }

    public void takeDamage(int amount){
        if ((this.health-amount)>0){
            this.health-=amount;
            System.out.println (this.name +" took damage of "+ amount + " hit points ");
        }else{
            this.die();
        }
    }


    public boolean takeItem(Item loot){
        for (int i = 0; i<4; i++) if (inventory[i]==null) {
            inventory[i]=loot;
            System.out.println(this.name+" picked up "+inventory[i] + " of "+inventory[i].getName());
            break;
        }
        return false;
    }







    public int getPosition(){
        return this.currentPosition;
    }

    public void setPosition(int index){
        this.currentPosition=index;
    }

    public int move(int moveIndex){
        this.currentPosition+=moveIndex;
        return this.currentPosition;
    }

    public boolean checkIfDead(){
        return this.isDead;
    }

    public void die(){
        System.out.println(this.name+" died.");
        this.isDead = true;
    }
}