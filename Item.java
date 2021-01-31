public class Item extends Object{
    private final String name, color;
    private final int hp;



    public Item(String name, String color, int hp){
        this.name = name;
        this.color = color;
        this.hp = hp;

    }
    public int get_Hp (int hp) {return hp;}
    public String get_Color (String color) {return color;}
    public String getName() {return this.name;}

    // need attribute of HP on item like item +15 hp

}