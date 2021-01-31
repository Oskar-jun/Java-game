public class Player extends keyCharacters {

    private final Item[] weared = new Item[ 3 ];

    public void wear( Item foundItem ) {
        for (int i = 0; i < this.weared.length; i++)
            if ( this.weared[ i ] == null ) {
                this.weared[ i ] = foundItem;
                i = this.weared.length;
            }
    }

    public Item[] wear( ) {
        System.out.println( " Player boosted his ship by " );
        return weared;
    }

    public Player( String name ) {
        super( name );
    }
}
