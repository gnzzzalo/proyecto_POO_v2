package upm.console.CLI__RunCommand;

import upm.console.CLI_Plan.CommandLineInterfacePlan;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class ShowMostRatedUserFuturePlansCommand extends CommandBase implements Command {
    private final CommandLineInterfacePlan cliPlan;

    public ShowMostRatedUserFuturePlansCommand(CommandLineInterfacePlan cliPlan, CommandLineInterface cli) {
        super(cli);
        this.cliPlan = cliPlan;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        this.cliPlan.showMostRatedUserFuturePlans();
        return false;
    }
}
