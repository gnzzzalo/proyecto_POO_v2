package upm.console;

import upm.console.CLI_Activity.CommandLineInterfaceActivity;
import upm.console.CLI_Plan.CommandLineInterfacePlan;
import upm.console.CLI__RunCommand.*;
import upm.console.CLI_User.CommandLineInterfaceUser;
import upm.data.models.User;
import upm.services.UserService;

import java.util.Optional;
import java.util.Scanner;

public class CommandLineInterface {

    private final View view;
    private final Command[] commands;
    private Optional<User> loggedUser;

    public CommandLineInterface(UserService userService, View view, CommandLineInterfaceUser commandLineInterfaceUser, CommandLineInterfacePlan commandLineInterfacePlan, CommandLineInterfaceActivity commandLineInterfaceActivity) {
        this.view = view;
        this.loggedUser = Optional.empty();

        this.commands = new Command[]{
                new CreateUserCommand(commandLineInterfaceUser),
                new LoginCommand(this, userService, view),
                new LogoutCommand(this, view),
                new CreateActivityCommand(commandLineInterfaceActivity, this),
                new CreatePlanCommand(commandLineInterfacePlan, this),
                new AddActivityToPlanCommand(commandLineInterfacePlan, this),
                new DeletePlanCommand(commandLineInterfacePlan, this),
                new TotalTimePlanCommand(commandLineInterfacePlan, this),
                new ShowPricePlanCommand(commandLineInterfacePlan, this),
                new ShowFuturePlansCommand(commandLineInterfacePlan, this),
                new ShowFuturePlansByRateCommand(commandLineInterfacePlan, this),
                new ShowFuturePlansByDateCommand(commandLineInterfacePlan, this),
                new ShowFuturePlansByUserNameCommand(commandLineInterfacePlan, this),
                new ShowMostRatedUserFuturePlansCommand(commandLineInterfacePlan, this),
                new ShowPastPlansByLoggedUserCommand(commandLineInterfacePlan, this),
                new SubscribeToPlanCommand(commandLineInterfaceUser, this),
                new UnsubscribeFromPlanCommand(commandLineInterfaceUser, this),
                new ShowSubscribedPlansCommand(commandLineInterfacePlan, this),
                new HelpCommand(view),
                new ExitCommand()
        };
    }

    public Optional<User> getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Optional<User> loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean runCommands() {
        Scanner scanner = new Scanner(System.in).useDelimiter("[:,\\r\\n]");
        boolean exit = false;
        while (!exit) {
            exit = runCommand(scanner);
        }
        return true;
    }

    private boolean runCommand(Scanner scanner) {
        this.view.showCommand();
        CommandNames commandNames = CommandNames.fromValue(scanner.next());

        if (commandNames != null && commandNames.ordinal() < commands.length) {
            return commands[commandNames.ordinal()].execute(scanner);
        } else {
            this.view.showError("Comando no reconocido.");
            return false;
        }
    }
}
