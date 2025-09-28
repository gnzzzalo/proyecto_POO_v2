package upm.console;

import upm.data.models.Activity;
import upm.data.models.Plan;

public class View {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String COPY_RIGHT = "\u00A9";
    public static String COMMAND = "gps>";

    public void show(String message) {
        System.out.println(View.PURPLE + "   - " + message + View.RESET);
    }

    public void showPlan(String message, Plan plan) {
        System.out.println(View.PURPLE + message + "id: " + plan.getId() + "; propietario: " +
                plan.getPropietary().getName() + "; nombre: " + plan.getName() + "; fecha: " + plan.getDateTime() + ";\nlugar: " + plan.getPlace() +
                "; aforo: " + plan.getCapacity() + "; duracion: " + plan.getTotalTime() + "; coste: " + plan.getGenericTotalPrice());
        System.out.println("actividades: ");
        for (Activity activity : plan.getActivityList()) {
            System.out.print(activity.getName() + "; ");
        }
        System.out.println(View.RESET);
    }

    public void showBold(String message) {
        System.out.println(View.RED + "  " + message + "  " + View.RESET);
    }

    public void showError(String message) {
        System.out.println(View.BACKGROUND_RED + View.BLACK + "  " + message + "  " + View.RESET);
    }

    public void showCommand() {
        System.out.print(View.BLUE + COMMAND);
    }
}
