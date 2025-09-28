package upm.console.CLI__RunCommand;

import upm.console.CommandLineInterface;
import upm.console.View;
import upm.services.exceptions.NotFoundException;

import java.util.Optional;
import java.util.Scanner;

public class LogoutCommand implements Command {
    private final CommandLineInterface cli;
    private final View view;

    public LogoutCommand(CommandLineInterface cli, View view) {
        this.cli = cli;
        this.view = view;
    }

    @Override
    public boolean execute(Scanner scanner) {
        if (cli.getLoggedUser().isEmpty()) {
            throw new NotFoundException("No estás loggeado");
        }
        cli.setLoggedUser(Optional.empty());
        View.COMMAND = "gps>";
        this.view.show("Sesión cerrada con éxito, ¡Hasta pronto!");
        return false;
    }
}
