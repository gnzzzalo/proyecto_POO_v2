package upm.console.CLI__RunCommand;

import upm.console.CommandNames;
import upm.console.View;

import java.util.Scanner;

public class HelpCommand implements Command {
    private final View view;

    public HelpCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean execute(Scanner scanner) {
        for (CommandNames command : CommandNames.values()) {
            view.show(command.getHelp());
        }
        return false;
    }
}

