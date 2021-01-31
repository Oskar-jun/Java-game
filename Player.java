public class Player extends keyCharacters {

    private final Item[] weared = new Item[ 3 ];

    public void wear( Item foundItem ) {                // By wearing boosters we increase hp of our player
        for (int i = 0; i < this.weared.length; i++)
            if ( this.weared[ i ] == null ) {
                this.weared[ i ] = foundItem;
                i = this.weared.length;
            }
    }

    public Player( String name ) {
        super( name );
    }
}
