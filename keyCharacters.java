public class keyCharacters extends Object {
    private final String name;
    private int health;
    private int currentPosition = 0;
    private boolean isDead = false;
    private final Item[] inventory = new Item[4];

    public keyCharacters( String name ) {
        this.name = name;
        this.health = 100;
    }

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
            System.out.println( this.name + " took damage of " + amount + " hit points " );
        } else {
            this.die( );
        }
    }

    public void healthincrease( int amount ) {
        this.health += amount;
        System.out.println( "Current health is " + this.health );
    }

    public boolean takeItem( Item item ) {
        for ( int i = 0; i < 4; i++ )
            if ( inventory[i] == null ) {
                inventory[i] = item;
                System.out.println( this.name + " picked up " + inventory[i].getName( ) );
                break;
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