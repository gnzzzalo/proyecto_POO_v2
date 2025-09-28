package upm.console.CLI__RunCommand;

import upm.console.CLI_Activity.CommandLineInterfaceActivity;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class CreateActivityCommand extends CommandBase implements Command {
    private final CommandLineInterfaceActivity cliActivity;

    public CreateActivityCommand(CommandLineInterfaceActivity cliActivity, CommandLineInterface cli) {
        super(cli);
        this.cliActivity = cliActivity;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        this.cliActivity.createActivity(scanner);
        return false;
    }
}
