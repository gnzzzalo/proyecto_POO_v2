package upm.console.CLI__RunCommand;

import upm.console.CLI_Plan.CommandLineInterfacePlan;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class ShowFuturePlansByUserNameCommand extends CommandBase implements Command {
    private final CommandLineInterfacePlan cliPlan;

    public ShowFuturePlansByUserNameCommand(CommandLineInterfacePlan cliPlan, CommandLineInterface cli) {
        super(cli);
        this.cliPlan = cliPlan;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        this.cliPlan.showFuturePlansByUserName(scanner);
        return false;
    }
}
