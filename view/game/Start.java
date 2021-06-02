package view.game;

import controller.GameManager;
import controller.Logger;
import view.AbstractMenu;

import java.util.regex.Matcher;

public class Start extends AbstractMenu {

    private GameManager gameManager;

    public Start(GameManager gameManager, int level) {
        super();
        this.gameManager = gameManager;
        this.gameManager.setGame(level);
        Logger.log("info", "The game was set!");
    }

    @Override
    public void run() {
        String command;
        Matcher matcher;
        while (!Commands.EXIT.getMatcher(command = scanner.nextLine()).matches()) {
            if ((matcher = Commands.ADD_PERSON.getMatcher(command)).matches()) {

            } else {
                System.out.println("Illegal command!");
            }
        }
    }
}
