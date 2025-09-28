package upm.console.CLI_Plan;

import upm.console.CommandNames;
import upm.console.View;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.services.PlanService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterfacePlan {
    private static final int SIN_LIMITE = -1;
    private final PlanService planService;
    private final View view;

    public CommandLineInterfacePlan(PlanService planService, View view) {
        this.planService = planService;
        this.view = view;
    }

    public void createPlan(Scanner scanner, User loggedUser) {
        String[] values = scanner.next().split(";");
        if (values.length != 4 && values.length != 5) {
            throw new IllegalArgumentException(CommandNames.CREATE_PLAN.getHelp());
        }
        int capacity = SIN_LIMITE;
        if (values.length == 5) {
            capacity = Integer.parseInt(values[4]);
        }
        if (LocalDateTime.now().isAfter(this.dateTime(values[2]))) {
            throw new IllegalArgumentException("El plan debe crearse para una fecha futura");
        }
        Plan plan = this.planService.create(new Plan(values[0], values[1], this.dateTime(values[2]), values[3], capacity), loggedUser);
        this.view.showPlan("Plan creado: ", plan);
    }

    public void addActivityToPlan(Scanner scanner, User loggedUser) {
        String[] values = scanner.next().split(";");
        if (values.length != 2) {
            throw new IllegalArgumentException(CommandNames.ADD_ACTIVITY_TO_PLAN.getHelp());
        }
        Plan plan = this.planService.addActivity(loggedUser, Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        this.view.show("Actividad añadida: " + plan.toString());
    }

    private LocalDateTime dateTime(String dateTime) {
        String[] fechaYHora = dateTime.split("-");
        String[] fecha = fechaYHora[0].split("/");
        String[] hora = fechaYHora[1].split(":");

        return LocalDateTime.of(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]), Integer.parseInt(hora[0]), Integer.parseInt(hora[1]));
    }

    public void deletePlan(Scanner scanner, User loggedUser) {
        String[] values = scanner.next().split(";");
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.DELETE_PLAN.getHelp());
        }
        Plan plan = this.planService.delete(Integer.parseInt(values[0]), loggedUser);
        this.view.show("Plan con id " + plan.getId() + "ha sido eliminado");
    }

    public void totalTimePlan(Scanner scanner) {
        String[] values = scanner.next().split(";");
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.TOTAL_TIME_PLAN.getHelp());
        }
        Integer totalTime = this.planService.getTotalTime(Integer.parseInt(values[0]));
        this.view.show("Tiempo del plan con id " + values[0] + ": " + totalTime + "min");
    }

    public void showFuturePlansByRate() {
        showPlanList(this.planService.getFuturePlansByRate());
    }

    public void showFuturePlansByDate() {
        showPlanList(this.planService.getFuturePlansByDate());
    }

    public void showFuturePlansByUserName(Scanner scanner) {
        String[] values = scanner.next().split(";");
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.SHOW_FUTURE_PLANS_BY_USER_NAME.getHelp());
        }
        showPlanList(this.planService.getFuturePlansByUserName(values[0]));
    }

    public void showPastPlansByLoggedUser(User loggedUser) {
        showPlanList(this.planService.getPastPlansByLoggedUser(loggedUser));
    }

    public void showMostRatedUserFuturePlans() {
        showPlanList(this.planService.getMostRatedUserFuturePlans());
    }

    public void showPricePlan(Scanner scanner, User loggedUser) {
        String[] values = scanner.next().split(";");
        if (values.length != 1) {
            throw new IllegalArgumentException(CommandNames.SHOW_PRICE_PLAN.getHelp());
        }
        String price = this.planService.getUserAndGenericPrice(loggedUser, Integer.parseInt(values[0]));
        this.view.show("Precio del plan con id " + values[0] + ": " + price);
    }

    public void showFuturePlans() {
        showPlanList(this.planService.getFuturePlans());
    }

    public void showSubscribedPlans(User loggedUser) {
        showPlanList(this.planService.getSubscribedPlans(loggedUser));
    }

    private void showPlanList(List<Plan> planList) {
        for (Plan plan : planList) {
            this.view.showPlan("", plan);
        }
    }
}
