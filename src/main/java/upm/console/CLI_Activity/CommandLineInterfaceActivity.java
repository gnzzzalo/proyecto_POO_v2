package upm.console.CLI_Activity;


import upm.console.CommandNames;
import upm.console.View;
import upm.data.models.ActivitiesTypes.Cinema;
import upm.data.models.ActivitiesTypes.Generic;
import upm.data.models.ActivitiesTypes.Theatre;
import upm.data.models.Activity;
import upm.data.repositories.ActivitiyUsages;
import upm.services.ActivityService;

import java.math.BigDecimal;
import java.util.Scanner;

public class CommandLineInterfaceActivity {
    private static final int SIN_LIMITE = -1;
    private final ActivityService activityService;
    private final View view;

    public CommandLineInterfaceActivity(ActivityService activityService, View view) {

        this.activityService = activityService;

        this.view = view;
    }

    public void createActivity(Scanner scanner) {
        Activity activity = this.activityService.create(selectType(scanner));
        this.view.show("Actividad creada: " + activity.toString());
    }

    private Activity selectType(Scanner scanner) {
        String[] values = scanner.next().split(";");
        if (values.length != 5 && values.length != 6) {
            throw new IllegalArgumentException(CommandNames.CREATE_ACTIVITY.getHelp());
        }
        int capacity = SIN_LIMITE;
        if (values.length == 6) {
            capacity = Integer.parseInt(values[5]);
        }
        String type = values[2];
        Activity activity;
        switch (type.toLowerCase()) {
            case ActivitiyUsages.CINEMA:
                activity = new Cinema(values[0], values[1], values[2], Integer.valueOf(values[3]), BigDecimal.valueOf(Long.parseLong(values[4])), capacity);
                break;
            case ActivitiyUsages.THEATRE:
                activity = new Theatre(values[0], values[1], values[2], Integer.valueOf(values[3]), BigDecimal.valueOf(Long.parseLong(values[4])), capacity);
                break;
            case ActivitiyUsages.GENERIC:
                activity = new Generic(values[0], values[1], values[2], Integer.valueOf(values[3]), BigDecimal.valueOf(Long.parseLong(values[4])), capacity);
                break;
            default:
                throw new IllegalArgumentException("Solo se admiten los siguientes tipos de actividad: " + ActivitiyUsages.CINEMA + ", " + ActivitiyUsages.GENERIC + " o " + ActivitiyUsages.THEATRE);
        }
        return activity;
    }
}
