package upm.console.CLI__RunCommand;

import upm.console.CLI_Plan.CommandLineInterfacePlan;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class AddActivityToPlanCommand extends CommandBase implements Command {
    private final CommandLineInterfacePlan commandLineInterfacePlan;

    public AddActivityToPlanCommand(CommandLineInterfacePlan commandLineInterfacePlan, CommandLineInterface cli) {
        super(cli);
        this.commandLineInterfacePlan = commandLineInterfacePlan;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        commandLineInterfacePlan.addActivityToPlan(scanner, getUser());
        return false;
    }
}
