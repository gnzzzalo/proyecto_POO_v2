package upm.console.CLI_User;

import upm.console.CommandNames;
import upm.console.View;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.services.UserService;

import java.util.Scanner;

public class CommandLineInterfaceUser {
    private final UserService userService;

    private final View view;

    public CommandLineInterfaceUser(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }

    public void createUser(Scanner scanner) {
        String[] values = scanner.next().split(";");
        if (values.length != 4) {
            throw new IllegalArgumentException(CommandNames.CREATE_USER.getHelp());
        }
        User createdUser = this.userService.create(new User(values[0], Integer.valueOf(values[1]), Integer.valueOf(values[2]), values[3]));
        this.view.show("Usuario creado: " + createdUser.toString());
    }

    public void subscribeToPlan(Scanner scanner, User loggedUser) {
        String[] values = scanner.next().split(";");
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.SUBSCRIBE_TO_PLAN.getHelp());
        }
        Plan plan = this.userService.subscribeToPlan(loggedUser, Integer.parseInt(values[0]));
        this.view.show("Suscrito al plan: " + plan.toString());
    }

    public void unsubscribeToPlan(Scanner scanner, User loggedUser) {
        String[] values = scanner.next().split(";");
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.UNSUBSCRIBE_TO_PLAN.getHelp());
        }
        Plan plan = this.userService.unsubscribeToPlan(loggedUser, Integer.parseInt(values[0]));
        this.view.show("Eliminada la suscripción al plan: " + plan.toString());
    }

}
