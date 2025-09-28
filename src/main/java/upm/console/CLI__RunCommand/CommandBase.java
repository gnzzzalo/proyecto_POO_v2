package upm.console.CLI__RunCommand;

import upm.console.CommandLineInterface;
import upm.data.models.User;

public class CommandBase {
    private final CommandLineInterface cli;

    public CommandBase(CommandLineInterface cli) {
        this.cli = cli;
    }

    protected User getUser() {
        if (cli.getLoggedUser().isPresent())
            return cli.getLoggedUser().get();
        else
            throw new IllegalArgumentException("No estás logeado, por favor, inicia sesión");
    }

    protected void checkLoggedUser() {
        if (cli.getLoggedUser().isEmpty()) {
            throw new IllegalArgumentException("No estás logeado, por favor, inicia sesión");
        }
    }

}
