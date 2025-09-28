package upm.console.CLI__RunCommand;

import upm.console.CLI_User.CommandLineInterfaceUser;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class SubscribeToPlanCommand extends CommandBase implements Command {
    private final CommandLineInterfaceUser cliUser;

    public SubscribeToPlanCommand(CommandLineInterfaceUser cliUser, CommandLineInterface cli) {
        super(cli);
        this.cliUser = cliUser;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        cliUser.subscribeToPlan(scanner, getUser());
        return false;
    }
}

