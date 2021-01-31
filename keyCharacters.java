public class keyCharacters extends Object {
    public static final String ANSI_ORANGE = "\033[48:5:166:0m\033[m\n";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final String name;
    private int health;
    private int currentPosition = 0;
    private boolean isDead = false;
    private final Item[] inventory = new Item[3];

    public keyCharacters( String name ) {
        this.name = name;
        this.health = 100;
    }

    public int getHealth( ) { return health; }

    public String getName( ) {
        return this.name;
    }

    public int move( int moveIndex ) {
        this.currentPosition += moveIndex;
        return this.currentPosition;
    }

    public void takenDamage( int amount ) {
        if ( ( this.health - amount ) > 0 ) {
            this.health -= amount;
            if ( amount > 0 )
                System.out.println( this.name + " took damage of " + amount + " hit points " );
            else if ( amount < 0 )
                System.out.println( ANSI_RESET + "Ship was fortified to +" + (amount * (-1)) );
        } else {
            this.die( );
        }
    }

    public boolean takeItem( Item item ) {
        for ( int i = 0; i < 3; i++ )
            if ( inventory[i] == null ) {
                inventory[i] = item;
                System.out.println( this.name + " upgraded his ship by " + inventory[i].getName( ) );
                return true;
            }
        return false;
    }

    public void setPosition( int index ) {
        this.currentPosition = index;
    }

    public int getPosition( ) {
        return this.currentPosition;
    }

    public boolean checkIfDead( ) {
        return this.isDead;
    }

    public void die( ) {
        System.out.println( this.name + " died." );
        this.isDead = true;
    }
}