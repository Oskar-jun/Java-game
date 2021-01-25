public class Item extends Object{
    private int rarity;
    private String name;

    public Item(int rarity, String name){
        this.rarity = rarity;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}