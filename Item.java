public class Item extends Object {
    private final String name;
    private final int hp;

    public Item( String name, int hp ) {
        this.name = name;
        this.hp = hp;

    }

    public int get_Hp( int hp ) {
        return hp;
    }

    public String get_Color( String color ) {
        return color;
    }

    public String getName( ) {
        return this.name;
    }

    // need attribute of HP on item like item +15 hp {{ in proceed

}