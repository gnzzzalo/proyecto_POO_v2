package upm.console.CLI__RunCommand;

import upm.console.CLI_Plan.CommandLineInterfacePlan;
import upm.console.CommandLineInterface;

import java.util.Scanner;

public class TotalTimePlanCommand extends CommandBase implements Command {
    private final CommandLineInterfacePlan cliPlan;

    public TotalTimePlanCommand(CommandLineInterfacePlan cliPlan, CommandLineInterface cli) {
        super(cli);
        this.cliPlan = cliPlan;
    }

    @Override
    public boolean execute(Scanner scanner) {
        checkLoggedUser();
        this.cliPlan.totalTimePlan(scanner);
        return false;
    }
}
