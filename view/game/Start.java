package view.game;

import controller.GameManager;
import controller.Logger;
import controller.ResultType;
import view.AbstractMenu;

import java.util.regex.Matcher;

public class Start extends AbstractMenu {

    private GameManager gameManager;

    public Start(GameManager gameManager, int level) {
        super();
        this.gameManager = gameManager;
        this.gameManager.setGame(level);
        Logger.log("info", "The game was set!");
        System.out.println("Game started!");
        System.out.println();
    }

    private void well() {
        ResultType result = gameManager.well();
        switch (result) {
            case EXISTED:
                System.out.println("Well is working now!");
                break;

            case NOT_ENOUGH:
                System.out.println("There is not enough coin!");
                break;

            case BAD_CONDITION:
                System.out.println("The water bucket is not empty!");
                break;

            case SUCCESS:
                System.out.println("The well started working!");
                break;
        }
    }

    private void plant(Matcher matcher) {
        ResultType result = gameManager.plant(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case INVALID_NUMBER:
                System.out.println("The coordinates are invalid!");
                break;

            case NOT_ENOUGH:
                System.out.println("There is no water!");
                break;

            case SUCCESS:
                System.out.println("Plant was successful!");
                break;
        }
    }

    private void pickup(Matcher matcher) {
        ResultType result = gameManager.pickup(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case INVALID_NUMBER:
                System.out.println("The coordinates are invalid!");
                break;

            case SUCCESS:
                System.out.println("The coordinates was checked!");
                break;

            case NOT_EXISTED:
                System.out.println("There is nothing to pickup!");
                break;

            case FINISH:
                System.out.println(result.getValue());
                break;
        }
    }

    private void buy(Matcher matcher) {
        ResultType result = gameManager.buy(matcher.group(1));
        switch (result) {
            case NOT_ENOUGH:
                System.out.println("There is not enough coin!");
                break;

            case SUCCESS:
                System.out.println("The animal was bought successfully!");
                break;

            case NOT_EXISTED:
                System.out.println("There is no such animal that can be bought!");
                break;

            case FINISH:
                System.out.println(result.getValue());
                break;
        }
    }

    private void build(Matcher matcher) {
        ResultType result = gameManager.build(matcher.group(1));
        switch (result) {
            case NOT_ENOUGH:
                System.out.println("There is not enough coin!");
                break;

            case SUCCESS:
                System.out.println("The factory was built successfully!");
                break;

            case NOT_EXISTED:
                System.out.println("There is no such factory that can be built!");
                break;

            case EXISTED:
                System.out.println("The factory already exists!");
                break;
        }
    }

    private void work(Matcher matcher) {
        ResultType result = gameManager.work(matcher.group(1), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case NOT_EXISTED:
                System.out.println("There is no such factory!");
                break;

            case EXISTED:
                System.out.println("The factory is working!");
                break;

            case INVALID_NUMBER:
                System.out.println("The number you entered is invalid!");
                break;

            case SUCCESS:
                System.out.println("The factory started successfully!");
                break;

            case NOT_ENOUGH:
                System.out.println("There are no enough goods in the warehouse!");
                break;
        }
    }

    private void cage(Matcher matcher) {
        ResultType result = gameManager.cage(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case INVALID_NUMBER:
                System.out.println("The coordinates are invalid!");
                break;

            case SUCCESS:
                System.out.println("The coordinates was checked!");
                break;

            case NOT_EXISTED:
                System.out.println("There is nothing to cage!");
                break;
        }
    }

    private void truckLoad(Matcher matcher) {
        ResultType result = gameManager.truckLoad(matcher.group(1), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case NOT_EXISTED:
                System.out.println("There is no such object!");
                break;

            case INVALID_NUMBER:
                System.out.println("The capacity of truck is not enough!");
                break;

            case BAD_CONDITION:
                System.out.println("The truck is working!");
                break;

            case SUCCESS:
                System.out.println("The object was loaded to the truck successfully!");
                break;

            case NOT_ENOUGH:
                System.out.println("There are no enough objects!");
                break;
        }
    }

    private void truckUnload(Matcher matcher) {
        ResultType result = gameManager.truckUnload(matcher.group(1), Integer.parseInt(matcher.group(2)));
        switch (result) {
            case NOT_EXISTED:
                System.out.println("There is no such object!");
                break;

            case INVALID_NUMBER:
                System.out.println("The capacity of warehouse is not enough!");
                break;

            case BAD_CONDITION:
                System.out.println("The truck is working!");
                break;

            case SUCCESS:
                System.out.println("The object was unloaded successfully!");
                break;

            case NOT_ENOUGH:
                System.out.println("There are no enough objects!");
                break;
        }
    }

    private void truckGo() {
        ResultType result = gameManager.truckGo();
        switch (result) {
            case EXISTED:
                System.out.println("The truck is working!");
                break;

            case SUCCESS:
                System.out.println("The truck went successfully!");
                break;

            case NOT_ENOUGH:
                System.out.println("There are no objects in the truck!");
                break;
        }
    }

    private void inquiry() {
        System.out.println(gameManager.inquiry());
    }

    private void turn(Matcher matcher) {
        ResultType result = gameManager.turn(Integer.parseInt(matcher.group(1)));
        switch (result) {
            case INVALID_NUMBER:
                System.out.println("The time is invalid!");
                break;

            case SUCCESS:
            case FINISH:
                System.out.println(result.getValue());
                break;
        }
    }

    private void upgrade(Matcher matcher) {
        ResultType result = gameManager.upgrade(matcher.group(1));
        switch (result) {
            case NOT_ENOUGH:
                System.out.println("There is not enough coin!");
                break;

            case SUCCESS:
                System.out.println(result.getValue() + " was upgraded successfully!");
                break;

            case NOT_EXISTED:
                System.out.println("There is no such object that can be upgraded!");
                break;
        }
    }

    @Override
    public void run() {
        String command;
        Matcher matcher;
        while (!Commands.EXIT.getMatcher(command = scanner.nextLine()).matches()) {
            if (gameManager.isFinished()) {
                if (Commands.MENU.getMatcher(command).matches()) {
                    break;
                } else {
                    System.out.println("Game finished! Enter MENU or EXIT to go to the game menu!");
                }
            } else if ((matcher = Commands.TRUCK_LOAD.getMatcher(command)).matches()) {
                truckLoad(matcher);
            } else if ((matcher = Commands.TRUCK_UNLOAD.getMatcher(command)).matches()) {
                truckUnload(matcher);
            } else if (Commands.TRUCK_GO.getMatcher(command).matches()) {
                truckGo();
            } else if (!gameManager.truckIsEmpty()) {
                System.out.println("The truck is not empty! You must finish your work with truck!");
            } else if (Commands.WELL.getMatcher(command).matches()) {
                well();
            } else if ((matcher = Commands.PLANT.getMatcher(command)).matches()) {
                plant(matcher);
            } else if ((matcher = Commands.PICKUP.getMatcher(command)).matches()) {
                pickup(matcher);
            } else if ((matcher = Commands.BUY.getMatcher(command)).matches()) {
                buy(matcher);
            } else if ((matcher = Commands.BUILD.getMatcher(command)).matches()) {
                build(matcher);
            } else if ((matcher = Commands.WORK.getMatcher(command)).matches()) {
                work(matcher);
            } else if ((matcher = Commands.CAGE.getMatcher(command)).matches()) {
                cage(matcher);
            } else if (Commands.INQUIRY.getMatcher(command).matches()) {
                inquiry();
            } else if ((matcher = Commands.TURN.getMatcher(command)).matches()) {
                turn(matcher);
            } else if ((matcher = Commands.UPGRADE.getMatcher(command)).matches()) {
                upgrade(matcher);
            } else {
                System.out.println("Illegal command!");
            }
            System.out.println();
        }
    }
}
