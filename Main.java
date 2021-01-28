import java.awt.*;
import java.util.Scanner;

public class Main{
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
    //Ico of kraken and pirates  🦑    🏴‍☠ ️     🚢           🐙  [🐲] - was 1st draft
    //Ico of edge of the world  🌊 - let`s imagine that its kinda waterfall :)                [☣] - was 1st draft
    //Ico of item 🧰 It`s expected that player takes item from the chest
    //Ico of fog 🌫️

    public static void mapRepresent(Object[] worldLayer, Object [] playerLayer){
        int gridNum=20;
        int startGrid=0;
        for (int i=0; i<40; i++){
            if(gridNum == 19)
                System.out.print(" ");
            for (int j = startGrid; j < startGrid+gridNum; j++){
                if (playerLayer[j]!=null){
                    System.out.print( ANSI_PURPLE + "\uD83D\uDEA3\u200D" + ANSI_RESET );
                }else if (worldLayer[j] instanceof Enemy){
                    if (((Enemy) worldLayer[j]).getName().equals("Ugly Kraken, fear of depth")) {  /* different Icons for
                                                                                                    the different enemies. Literally doing same,
                                                                                                    but more interesting :) */
                        System.out.print(ANSI_RED + "\uD83E\uDD91" + ANSI_RESET);
                    }else if(((Enemy) worldLayer[j]).getName().equals("Pirates of the Caribbean"))
                        System.out.print(ANSI_RED + "☠" + ANSI_RESET);
                }else if (worldLayer[j] instanceof Item){
                    System.out.print( ANSI_YELLOW + "\uD83E\uDDF0" + ANSI_RESET);
                }else{
                    System.out.print( ANSI_CYAN + "\uD83C\uDF2B️" + ANSI_RESET);
                }
            }
            startGrid+=gridNum;
            if (gridNum==20){
                gridNum=19;
            }else{
                gridNum=20;
            }
            System.out.println();
            System.out.print(" ");
        }
    }


    public static void moveMain(int index, Player currentPlayer, Object[] world_layer, Object[] player_layer){
        player_layer[currentPlayer.getPosition()]=null;
        player_layer[currentPlayer.move(index)]=currentPlayer;

        if (world_layer[currentPlayer.getPosition()]!=null){
            System.out.println( ANSI_YELLOW + "There is something here..." + ANSI_RESET);

            if (world_layer[currentPlayer.getPosition()] instanceof Enemy){
                System.out.println("It is an enemy!");
                fight(currentPlayer, (Enemy)world_layer[currentPlayer.getPosition()], world_layer);

            }
            if (world_layer[currentPlayer.getPosition()] instanceof Item){
                interact(currentPlayer, (Item)world_layer[currentPlayer.getPosition()], world_layer);
            }
        }
    }

    public static void interact(Player currentPlayer, Item foundItem, Object[] world_layer){
        System.out.println("If you want to pick up "+foundItem.getName()+" press E, then Enter, or Enter to skip it ");
        Scanner scanner = new Scanner(System.in);
        String interact = scanner.nextLine();
        boolean interaction; //small boolean
        switch (interact) {
            case ("E"), ("e") -> {
                interaction = true;
                if(currentPlayer.takeItem(foundItem)){
                    currentPlayer.getWeared(foundItem);                                           // Непонятная хрень, доделать
                    if ((foundItem.getName().equals("Super durable sail fabric")))
                        currentPlayer.healthincrease(10);
                    else if ((foundItem.getName().equals("Stern from hardened wood")))
                        currentPlayer.healthincrease(25);
                    else if ((foundItem.getName().equals("Heat-resistant ship bow")))
                        currentPlayer.healthincrease(50);
                    world_layer[currentPlayer.getPosition()] = null;
                }
            }
        }


        if (interaction = true){
            currentPlayer.healthincrease(50);
        }else{
            interaction = false;
        }
    }

    public static void fight(Player currentPlayer, Enemy currentEnemy, Object[] world_layer){
        while ((!currentEnemy.checkIfDead())&&(!currentPlayer.checkIfDead())){
            switch ((int) (Math.random() * 6)) {
                case (0) -> currentPlayer.takeDamage((int) (Math.random() + 11));
                case (1) -> {
                    System.out.println("Enemy missed" + ANSI_RED + " CRITICALLY." + ANSI_RESET);
                    currentEnemy.takeDamage((int) (Math.random() + 10));
                }
                case (3) -> currentEnemy.takeDamage((int) (Math.random() + 16));
                case (4) -> {
                    System.out.println("You missed" + ANSI_RED + " \uD835\uDC02\uD835\uDC11\uD835\uDC08\uD835\uDC13\uD835\uDC08\uD835\uDC02\uD835\uDC00\uD835\uDC0B\uD835\uDC0B\uD835\uDC18 " + ANSI_RESET); // special font of Crit. miss
                    currentPlayer.takeDamage( (int) Math.random() + 16);
                }
            }
        }
        if (currentEnemy.checkIfDead()) world_layer[currentPlayer.getPosition()]=null;
    }

    public static Object[] worldPregen() {
        double itemSpawnChance = 0.0, enemySpawnChance = 0.0;
        Object[] world = new Object[780]; // firstly I did 143 hexagonal grid, than 480, than 780. Think that it will be enough.
        for (int i = 0; i < 780; i++) {
            if ((int) (Math.random() + itemSpawnChance) >= 1) {
                world[i] = new Ship_Boosters( ANSI_CYAN+ "Super durable sail fabric" + ANSI_RESET);   /* 3 intuitive levels of items, blue - ordinary, purple - epic and yellow - legendary.
                                                                                                            At least at that point of time I expect myself to give some HP upgrade according to the class of item  */
                itemSpawnChance = 0.0;
            } else {
                itemSpawnChance += 0.000961;
                if ((int) (Math.random() + itemSpawnChance) >= 1) {
                    world[i] = new Ship_Boosters( ANSI_PURPLE + "Stern from hardened wood" + ANSI_RESET);
                    itemSpawnChance = 0.0;
                } else {
                    itemSpawnChance += 0.05961;
                    if ((int) (Math.random() + itemSpawnChance) >= 1) {
                        world[i] = new Ship_Boosters( ANSI_YELLOW + "Heat-resistant ship bow" + ANSI_RESET);
                        itemSpawnChance = 0.0;
                    } else {
                        itemSpawnChance += 0.05961;
                        if ((int) (Math.random() + enemySpawnChance) >= 1) {
                            world[i] = new Enemy("Ugly Kraken, fear of depth");
                            enemySpawnChance = 0.0;
                        } else {
                            enemySpawnChance += 0.05961;
                            if ((int) (Math.random() + enemySpawnChance) >= 1) {
                                world[i] = new Enemy("Pirates of the Caribbean");
                                enemySpawnChance = 0.0;
                            } else {
                                enemySpawnChance += 0.05961;
                            }
                        }
                    }
                }
            }
        }
        return world;
    }



    public static void main (String[] args){
        Player test_player1 = new Player("Alex");
        Object[] world_layer = worldPregen();
        Object[] player_layer = new Object[780];
        Scanner scannerMain = new Scanner(System.in);
        player_layer[439]=test_player1;
        test_player1.setPosition(439); //World is having a Hexagons grid so we are starting in +- the middle of it.
        while (!test_player1.checkIfDead()){
            mapRepresent(world_layer, player_layer);
            System.out.println("Make your move:" + ANSI_GREEN + " W " + ANSI_RESET + "for going [\uD83E\uDC81] to North," + ANSI_GREEN + " E " + ANSI_RESET + "for going [\uD83E\uDC85] to North-East," + ANSI_GREEN + " X " + ANSI_RESET +  "for going [\uD83E\uDC86] to South-East," + ANSI_GREEN + " S " + ANSI_RESET + " for going [\uD83E\uDC83] to South," + ANSI_GREEN + " Z " + ANSI_RESET + "for going [\uD83E\uDC87] to South-West," + ANSI_GREEN + " Q " + ANSI_RESET + "for going [\uD83E\uDC84] to North-West , then press enter");
            String move = scannerMain.nextLine();
            switch (move){
                case("W"):
                case("w"):
                    moveMain(-39, test_player1, world_layer, player_layer);
                    break;
                case("E"):
                case("e"):
                    moveMain(-19, test_player1, world_layer, player_layer);
                    break;
                case("X"):
                case("x"):
                    moveMain(20, test_player1, world_layer, player_layer);
                    break;
                case("S"):
                case("s"):
                    moveMain(39, test_player1, world_layer, player_layer);
                    break;
                case("Z"):
                case("z"):
                    moveMain(19, test_player1, world_layer, player_layer);
                    break;
                case("Q"):
                case("q"):
                    moveMain(-20, test_player1, world_layer, player_layer);
                    break;
            }
        }
        System.out.println("Game over.");

        // 1 Complete Edge of the world
        // Hz what else
        // 2 Item Hp increase system
        // auto weraing of items
        // differinciation of items
        // Different classes for pirates and kraken  }}Done



    }
}