package upm.console;

public class ErrorHandler {
    private final CommandLineInterface commandLineInterface;
    private final View view;

    public ErrorHandler(CommandLineInterface commandLineInterface, View view) {
        this.commandLineInterface = commandLineInterface;
        this.view = view;
        this.view.showBold("App Gestión de Planes Sociales (GPS). " + View.COPY_RIGHT + "UPM.ETSISI.POO");
        this.view.showBold("Para ver los comandos, introduce 'help'");
    }

    public void handlesErrors() {
        boolean exit = false;
        while (!exit) {
            try {
                exit = this.commandLineInterface.runCommands();
            } catch (Exception e) {
                this.view.showError(">>> ERROR (" + e.getClass().getSimpleName() + ") >>> " + e.getMessage());
            }
        }
        this.view.showBold("Hasta pronto!");
    }
}
