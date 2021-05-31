package view.home;

import controller.Authenticator;
import controller.Logger;
import view.AbstractMenu;
import view.game.Menu;

public class Login extends AbstractMenu {

    public Login() {
        super();
    }

    @Override
    public void run() {
        Authenticator authenticator = Authenticator.getInstance();
        String username;

        System.out.println("Enter your username:");
        if (!authenticator.checkUserExists((username = scanner.nextLine()))) {
            Logger.log("error", "The user entered a username not existing.");
            System.out.println("The username doesn't exist!");
            System.out.println();
            return;
        }

        Logger.log("info", "User " + username);
        System.out.println("Enter your password:");
        if (authenticator.checkIncorrectPassword(username, scanner.nextLine())) {
            Logger.log("error", "The user entered an incorrect password.");
            System.out.println("Your password is incorrect!");
            System.out.println();
            return;
        }

        System.out.println();
        Logger.log("info", username + " entered the game menu.");
        new Menu(authenticator.findUser(username)).run();
    }
}
