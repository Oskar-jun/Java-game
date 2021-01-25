public class Humanoid extends Object{
    private String name;
    private int health;
    private int mana;
    private int level;
    private int currentPosition=0;
    private Item[] inventory = new Item[24];
    private Skill[] skills = new Skill[5];
    private boolean isDead=false;

    public Humanoid(String name, Item[] inventory, Skill[] skills) {
        this.name = name;
        this.health = 50;
        this.mana = 100;
        this.inventory = inventory;
        this.skills = skills;
    }
    public void healthincrease(int amount){
        if ((100)>(this.health+amount)){
            this.health+=amount;
            System.out.println ("Current health is "+ this.health);
        }else{
            this.health=100;
        }
    }

    public void heal(int amount){
        if ((100+skills[0].toInt())>(this.health+amount)){
            this.health+=amount;
            System.out.println ("Current health is "+ this.health);
        }else{
            this.health=100+skills[0].toInt();
        }
    }

    public void takeDamage(int amount){
        if ((this.health-amount)>0){
            this.health-=amount;
            System.out.println (this.name +" took damage of "+ amount + "hit points");
        }else{
            this.die();
        }
    }

    public void getMana(int amount){
        if ((100+skills[1].toInt())>(this.mana+amount)){
            this.mana+=amount;
            System.out.println ("Current mana is "+ this.mana);
        }else{
            this.mana=100+skills[1].toInt();
        }
    }

    public void spendMana(int amount){
        if ((this.mana-amount)>0){
            this.mana-=amount;
            System.out.println ("Current mana is "+ this.mana);
        }else{
            System.out.println("Not enough mana");
        }
    }

    public void upgradeSkill(int index, int points){
        if ((index>=0)&&(index<4)) skills[index].upgrade(points);
        System.out.println(skills[index].toString());
    }

    public int getSkill(int index){
        return skills[index].toInt();
    }

    public void takeItem(Item loot){
        for (int i = 0; i<24; i++) if (inventory[i]==null) {
            inventory[i]=loot;
            System.out.println(this.name+" picked up "+inventory[i].getName());
            break;
        }
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