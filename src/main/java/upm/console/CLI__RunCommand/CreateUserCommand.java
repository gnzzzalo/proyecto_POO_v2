package upm.console.CLI__RunCommand;

import upm.console.CLI_User.CommandLineInterfaceUser;

import java.util.Scanner;

public class CreateUserCommand implements Command {
    private final CommandLineInterfaceUser cliUser;

    public CreateUserCommand(CommandLineInterfaceUser cliUser) {
        this.cliUser = cliUser;
    }

    @Override
    public boolean execute(Scanner scanner) {
        cliUser.createUser(scanner);
        return false;
    }
}