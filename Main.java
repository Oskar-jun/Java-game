import java.util.Scanner;

public class Main{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


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
                currentPlayer.takeItem(foundItem);
                currentPlayer.healthincrease(50);
                world_layer[currentPlayer.getPosition()] = null;
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
            switch ((int)(Math.random()*6)){
                //0-2 enemy act
                case(0):
                    currentPlayer.takeDamage((int)(Math.random()*(currentEnemy.getSkill(2)))+10);
                    break;
                case(1):
                    System.out.println("Enemy missed.");
                    break;
                case(2):
                    System.out.println("Enemy missed" + ANSI_RED + " CRITICALLY." +ANSI_RESET);
                    currentEnemy.takeDamage((int)(Math.random()*(currentEnemy.getSkill(2)))+10);
                    break;
                //3-5 player act
                case(3):
                    currentEnemy.takeDamage((int)(Math.random()*(currentPlayer.getSkill(2)))+15);
                    break;
                case(4):
                    System.out.println("You missed.");
                    break;
                case(5):
                    System.out.println("You missed" + ANSI_RED +  " \uD835\uDC02\uD835\uDC11\uD835\uDC08\uD835\uDC13\uD835\uDC08\uD835\uDC02\uD835\uDC00\uD835\uDC0B\uD835\uDC0B\uD835\uDC18 " +ANSI_RESET); // special font of Crit. miss
                    currentPlayer.takeDamage((int)(Math.random()*(currentPlayer.getSkill(2)))+15);
                    break;
            }
        }
        if (currentEnemy.checkIfDead()) world_layer[currentPlayer.getPosition()]=null;
    }

    public static Object[] worldPregen() {
        double itemSpawnChance = 0.0;
        //int itemCount = 0;
        //int enemyCount = 0;
        double enemySpawnChance = 0.0;
        Object[] world = new Object[380];
        for (int i = 0; i < 380; i++) {
            if ((int) (Math.random() + itemSpawnChance) >= 1) {
                world[i] = new Clothes(1, "Body armor", (int) (Math.random() * 4));
                itemSpawnChance = 0.0;
                //itemCount+=1;
            } else {
                itemSpawnChance += 0.000961;// will be spawned only 2-4 of armors for the full map
                if ((int) (Math.random() + itemSpawnChance) >= 1) {
                    world[i] = new Clothes(2, "iron gloves", (int) (Math.random() * 4));
                    itemSpawnChance = 0.0;
                } else {
                    itemSpawnChance += 0.09961;
                    if ((int) (Math.random() + itemSpawnChance) >= 1) {
                        world[i] = new Clothes(2, "ring-mail pants", (int) (Math.random() * 4));
                        itemSpawnChance = 0.0;
                    } else {
                        itemSpawnChance += 0.09961;
                            if ((int) (Math.random() + enemySpawnChance) >= 1) {
                                world[i] = new Enemy("Ugly demon", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0)});
                                enemySpawnChance = 0.0;
                                //enemyCount+=1;
                            } else {
                                enemySpawnChance += 0.00961;
                            }
                        }
                    }
                }
            }
        return world;
    }


    public static void main (String[] args){
        Player test_player1 = new Player("John", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0)});
        Object[] world_layer = worldPregen();
        Object[] player_layer = new Object[380];
        Scanner scannerMain = new Scanner(System.in);
        player_layer[186]=test_player1;
        test_player1.setPosition(186); //World is having a Hexagons grid so we are starting in the middle of it.
        while (!test_player1.checkIfDead()){
            System.out.println("Make your move:" + ANSI_GREEN + " W " + ANSI_RESET + "for going [\uD83E\uDC81] to North," + ANSI_GREEN + " E " + ANSI_RESET + "for going [\uD83E\uDC85] to North-East," + ANSI_GREEN + " X " + ANSI_RESET +  "for going [\uD83E\uDC86] to South-East," + ANSI_GREEN + " S " + ANSI_RESET + " for going [\uD83E\uDC83] to South," + ANSI_GREEN + " Z " + ANSI_RESET + "for going [\uD83E\uDC87] to South-West," + ANSI_GREEN + " Q " + ANSI_RESET + "for going [\uD83E\uDC84] to North-West , then press enter");
            String move = scannerMain.nextLine();
            switch (move){
                case("W"):
                case("w"):
                    moveMain(-19, test_player1, world_layer, player_layer);
                    break;
                case("E"):
                case("e"):
                    moveMain(-9, test_player1, world_layer, player_layer);
                    break;
                case("X"):
                case("x"):
                    moveMain(10, test_player1, world_layer, player_layer);
                    break;
                case("S"):
                case("s"):
                    moveMain(19, test_player1, world_layer, player_layer);
                    break;
                case("Z"):
                case("z"):
                    moveMain(9, test_player1, world_layer, player_layer);
                    break;
                case("Q"):
                case("q"):
                    moveMain(-10, test_player1, world_layer, player_layer);
                    break;
            }
        }
        System.out.println("Game over.");


    }
}