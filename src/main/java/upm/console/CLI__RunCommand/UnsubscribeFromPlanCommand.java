package upm.console.CLI__RunCommand;

import upm.console.CLI_User.CommandLineInterfaceUser;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class UnsubscribeFromPlanCommand extends CommandBase implements Command {
    private final CommandLineInterfaceUser cliUser;

    public UnsubscribeFromPlanCommand(CommandLineInterfaceUser cliUser, CommandLineInterface cli) {
        super(cli);
        this.cliUser = cliUser;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        cliUser.unsubscribeToPlan(scanner, getUser());
        return false;
    }
}

