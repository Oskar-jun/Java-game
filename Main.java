import java.util.Scanner;

public class Main {
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
    //Ico of player 🚶   🚢   🚣‍     I have been choosing between this icons ️
    //Ico of kraken and pirates  🦑,    ,🏴‍☠ ️     ,🚢           ,🐙  [🐲] - was 1st draft
    //Ico of edge of the world  🌌  smth like a portal                              🕳️  🌊 [☣] - was 1st draft
    //Ico of item 🧰 It`s expected that player takes item from the chest
    //Ico of water 🌫️

    public static void mapRepresent( Object[] worldLayer, Object[] playerLayer ) {
        int gridNum = 20;
        int startGrid = 0;
        for (int i = 0; i < 40; i++) {
            if ( gridNum == 19 )
                System.out.print( " " );
            for (int j = startGrid; j < startGrid + gridNum; j++) {
                if ( playerLayer[ j ] != null ) {
                    System.out.print( ANSI_PURPLE + "\uD83D\uDEA3\u200D" + ANSI_RESET );
                } else if ( worldLayer[ j ] instanceof Enemy ) {
                    if ( ( ( Enemy ) worldLayer[ j ] ).getName( ).equals( "Ugly Kraken, fear of depth" ) ) {  /* different Icons for
                                                                                                                 the different enemies. Literally doing same,
                                                                                                                 but more interesting :) */
                        System.out.print( ANSI_RED + "\uD83E\uDD91" + ANSI_RESET );
                    } else if ( ( ( Enemy ) worldLayer[ j ] ).getName( ).equals( "Pirates of the Caribbean" ) )
                        System.out.print( ANSI_RED + "☠ " + ANSI_RESET );
                } else if ( worldLayer[ j ] instanceof Item ) {
                    System.out.print( ANSI_YELLOW + "\uD83E\uDDF0 " + ANSI_RESET );
                } else if ( worldLayer[ j ] instanceof Edge_Of_The_World ) {
                    System.out.print( "\uD83C\uDF0C " ); //  Edge of the world
                } else {
                    System.out.print( ANSI_CYAN + "\uD83C\uDF2B " + ANSI_RESET );
                }
            }
            startGrid += gridNum;
            if ( gridNum == 20 ) {
                gridNum = 19;
            } else {
                gridNum = 20;
            }
            System.out.println( );
            System.out.print( " " );
        }
    }

    public static void interact( Player currentPlayer, Item foundItem, Object[] world_level ) {
        System.out.println( "If you want to pick up " + foundItem.getName( ) + " press E, then Enter, or Enter to skip it " );
        Scanner scanner = new Scanner( System.in );
        String interact = scanner.nextLine( );
        switch ( interact ) {
            case ( "E" ), ( "e" ) -> {
                if ( currentPlayer.takeItem( foundItem )) {
                    currentPlayer.wear( foundItem );
                    if (foundItem.getName().equals( "Super durable sail fabric" )) {
                        currentPlayer.takenDamage    ( -10 );                                           // by negative numbers we add hp to ship
                    } else if (foundItem.getName().equals( "Stern from hardened wood" )) {
                        currentPlayer.takenDamage    ( -25 );
                    } else currentPlayer.takenDamage ( -50 );
                }
                checkItem( world_level, foundItem );
                System.out.println("Total health now is " + ANSI_RED
                        +  currentPlayer.getHealth() + ANSI_PURPLE);
                world_level[ currentPlayer.getPosition( ) ] = null;
            }
        }
    }

    public static void moveMain( Player currentPlayer, int index, Object[] world_layer, Object[] player_level ) {
        player_level[ currentPlayer.getPosition( ) ] = null;
        player_level[ currentPlayer.move( index ) ] = currentPlayer;

        if ( world_layer[ currentPlayer.getPosition( ) ] != null ) {
            System.out.println( ANSI_YELLOW + "There is something here..." + ANSI_RESET );


            if ( world_layer[ currentPlayer.getPosition( ) ] instanceof Edge_Of_The_World ) {
                currentPlayer.die( );
                System.out.print( ANSI_GREEN + "That is the end great adventurer! Mighty elders warned you that our world is flat and thus, who faced the edge of it, never came back. You fall to the" + ANSI_PURPLE + " black hole of infinity " + ANSI_RESET );
            }

            if ( world_layer[ currentPlayer.getPosition( ) ] instanceof Enemy ) {
                System.out.println( "It is an enemy!" );
                fight( currentPlayer, ( Enemy ) world_layer[ currentPlayer.getPosition( ) ], world_layer );
            }
            if ( world_layer[ currentPlayer.getPosition( ) ] instanceof Item ) {
                interact( currentPlayer, ( Item ) world_layer[ currentPlayer.getPosition( ) ], world_layer );
            }
        }
    }

    public static Object[] worldPregen( ) {
        double itemSpawnChance = 0.0, enemySpawnChance = 0.0;
        Object[] world = new Object[ 780 ]; // firstly I did 143 hexagonal grid, than 480, than 780. Think that it will be enough.
        for (int i = 0; i < 780; i++) {

            if ( ( int ) ( Math.random( ) + itemSpawnChance ) >= 1 ) {
                world[ i ] = new Ship_Boosters( ANSI_CYAN + "Super durable sail fabric",10 );   /* 3 intuitive levels of items, blue - ordinary, purple - epic and yellow - legendary.
                                                                                                                            At least at that point of time I expect myself to give some HP upgrade according to the class of item.
                                                                                                                            Change of the color of Upper "Edge of the world" portals was "Bug" but I decided to make it as a feature:
                                                                                                                            If world is flat, so upper portals are like generating the treasure chests for the other world, being linked
                                                                                                                            by such a portals :)
                                                                                                                            */

                itemSpawnChance = 0.0;
            } else {
                itemSpawnChance += 0.000961;
                if ( ( int ) ( Math.random( ) + itemSpawnChance ) >= 1 ) {
                    world[ i ] = new Ship_Boosters( ANSI_PURPLE + "Stern from hardened wood",25 ); // I know that it`s not the best way but I found no other option to reset the color.

                    itemSpawnChance = 0.0;
                } else {
                    itemSpawnChance += 0.05961;
                    if ( ( int ) ( Math.random( ) + itemSpawnChance ) >= 1 ) {
                        world[ i ] = new Ship_Boosters( ANSI_YELLOW + "Heat-resistant ship bow",50 );

                        itemSpawnChance = 0.0;
                    } else {
                        itemSpawnChance += 0.05961;
                        if ( ( int ) ( Math.random( ) + enemySpawnChance ) >= 1 ) {
                            world[ i ] = new Enemy( "Ugly Kraken, fear of depth" );
                            enemySpawnChance = 0.0;
                        } else {
                            enemySpawnChance += 0.05961;
                            if ( ( int ) ( Math.random( ) + enemySpawnChance ) >= 1 ) {
                                world[ i ] = new Enemy( "Pirates of the Caribbean" );
                                enemySpawnChance = 0.0;
                            } else {
                                enemySpawnChance += 0.05961;

                            }
                        }
                    }
                }
            }
            for (int j = 0; j < 742; j += 39)
                world[ j ] = new Edge_Of_The_World( );         // counting from zero so index of array will be +1
            for (int k = 1; k < 39; k++)                   // counting from 1
                world[ k ] = new Edge_Of_The_World( );
            for (int l = 19; l < 760; l += 39)              // same count from 20-1=19
                world[ l ] = new Edge_Of_The_World( );
            for (int l = 779; l > 741; l--)              // indexes therefore -1
                world[ l ] = new Edge_Of_The_World( );
        }
        return world;
    }

    public static void fight( Player currentPlayer, Enemy currentEnemy, Object[] world_level ) {
        while ( ( !currentEnemy.checkIfDead( ) ) && ( !currentPlayer.checkIfDead( ) ) ) {
            switch ( ( int ) ( Math.random( ) * 6 ) ) {
                case ( 0 ) -> currentPlayer.takenDamage( ( int ) ( Math.random( ) + 11 ) );
                case ( 1 ) -> {
                    System.out.println( "Enemy missed" + ANSI_RED + " CRITICALLY." + ANSI_RESET );
                    currentEnemy.takenDamage( ( int ) ( Math.random( ) + 10 ) );
                    currentEnemy.takenDamage( ( int ) ( Math.random( ) + 10 ) );
                }
                case ( 3 ) -> currentEnemy.takenDamage( ( int ) ( Math.random( ) + 16 ) );
                case ( 4 ) -> {
                    System.out.println( "You missed" + ANSI_RED + " \uD835\uDC02\uD835\uDC11\uD835\uDC08\uD835\uDC13\uD835\uDC08\uD835\uDC02\uD835\uDC00\uD835\uDC0B\uD835\uDC0B\uD835\uDC18 " + ANSI_RESET ); // special font of Crit. miss
                    if ( currentEnemy.getName( ).equals( "Ugly Kraken, fear of depth" ) ) {
                        currentPlayer.takenDamage( ( int ) Math.random( ) + 50 ); // as far as its GREAT KRAKEN, We are almost always dead when meeting him
                    } else currentPlayer.takenDamage( ( int ) Math.random( ) + 10 );
                }
            }
        }
        if ( currentEnemy.checkIfDead( ) ) world_level[ currentPlayer.getPosition( ) ] = null;
    }

   public static void  checkItem (  Object[] world_level ,Item foundItem ) {
        for ( int i = 0; i < 780; i++ ) {
            if ( ( world_level[i] instanceof Item ) && ( ( Item ) world_level[i] ).getName( ).equals( foundItem.getName( ) ) )
                world_level[i] = null;
        }
    }

    public static void main( String[] args ) {
        Player player_Alex = new Player( "Alex" );
        Object[] world_level = worldPregen( );
        Object[] player_level = new Object[ 780 ];
        Scanner scannerMain = new Scanner( System.in );
        player_level[ 439 ] = player_Alex;
        player_Alex.setPosition( 439 ); //World is having a Hexagons grid so we are starting in +- the middle of it.
        while ( !player_Alex.checkIfDead( ) ) {
            mapRepresent( world_level, player_level );
            System.out.println( "Make your move:" + ANSI_GREEN + " W " + ANSI_RESET + "for going [\uD83E\uDC81] to North," + ANSI_GREEN + " E " + ANSI_RESET + "for going [\uD83E\uDC85] to North-East," + ANSI_GREEN + " X " + ANSI_RESET + "for going [\uD83E\uDC86] to South-East," + ANSI_GREEN + " S " + ANSI_RESET + " for going [\uD83E\uDC83] to South," + ANSI_GREEN + " Z " + ANSI_RESET + "for going [\uD83E\uDC87] to South-West," + ANSI_GREEN + " Q " + ANSI_RESET + "for going [\uD83E\uDC84] to North-West , then press enter" );
            String move = scannerMain.nextLine( );
            switch ( move ) {
                case ( "W" ), ( "w" ) -> moveMain( player_Alex, -39, world_level, player_level );
                case ( "E" ), ( "e" ) -> moveMain( player_Alex, -19, world_level, player_level );
                case ( "X" ), ( "x" ) -> moveMain( player_Alex, 20, world_level, player_level );
                case ( "S" ), ( "s" ) -> moveMain( player_Alex, 39, world_level, player_level );
                case ( "Z" ), ( "z" ) -> moveMain( player_Alex, 19, world_level, player_level );
                case ( "Q" ), ( "q" ) -> moveMain( player_Alex, -20, world_level, player_level );
            }
        }




        System.out.println( "\n" + ANSI_RED + " Game over. " );

        // 1 Complete Edge of the world             }} Done
        // Hz what else
        // 2 Item Hp increase system
        // auto weraing of items
        // differinciation of items
        // Different classes for pirates and kraken  }}Done

        /* Also, I had one perfect idea which I hope I will realize later on on my own. That`s the idea of making world infinite
        by using edge of the world as a drop or teleport to the middle of new playing grid, making it not only cool legend, but also working algorythm.
         */


    }
}