package upm.console.CLI__RunCommand;

import upm.console.View;

import java.util.Scanner;

public class ExitCommand implements Command {
    @Override
    public boolean execute(Scanner scanner) {
        View.COMMAND = "gps>";
        return true;
    }
}
