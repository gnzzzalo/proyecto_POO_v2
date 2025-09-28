package upm.console.CLI__RunCommand;

import upm.console.CommandLineInterface;
import upm.console.CommandNames;
import upm.console.View;
import upm.data.models.User;
import upm.services.UserService;

import java.util.Optional;
import java.util.Scanner;

public class LoginCommand implements Command {
    private final CommandLineInterface cli;
    private final UserService userService;
    private final View view;

    public LoginCommand(CommandLineInterface cli, UserService userService, View view) {
        this.cli = cli;
        this.userService = userService;
        this.view = view;
    }

    @Override
    public boolean execute(Scanner scanner) {
        String[] values = scanner.next().split(";");
        if (values.length != 2) {
            throw new IllegalArgumentException(CommandNames.LOGIN.getHelp());
        }
        User userLogged = userService.findUserByName(values[0]);
        if (!userLogged.getPassword().equals(values[1])) {
            throw new IllegalArgumentException("La contraseña es incorrecta: " + values[1]);
        }
        cli.setLoggedUser(Optional.of(userLogged));
        View.COMMAND = "gps-" + values[0] + ">";
        this.view.show("¡Sesión iniciada con éxito!");
        return false;
    }
}

