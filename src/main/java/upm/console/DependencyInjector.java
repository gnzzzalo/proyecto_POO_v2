package upm.console;


import upm.console.CLI_Activity.CommandLineInterfaceActivity;
import upm.console.CLI_Plan.CommandLineInterfacePlan;
import upm.console.CLI_User.CommandLineInterfaceUser;
import upm.data.repositories.ActivityRepository;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;
import upm.data.repositories.repositories_map.ActivityRepositoryMap;
import upm.data.repositories.repositories_map.PlanRepositoryMap;
import upm.data.repositories.repositories_map.UserRepositoryMap;
import upm.services.ActivityService;
import upm.services.PlanService;
import upm.services.UserService;

public class DependencyInjector {
    private static DependencyInjector dependencyInjector;
    private final ErrorHandler errorHandler;
    private final View view;
    private final CommandLineInterface commandLineInterface;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final PlanRepository planRepository;
    private final UserService userService;
    private final ActivityService activityService;
    private final PlanService planService;
    private final CommandLineInterfaceUser commandLineInterfaceUser;
    private final CommandLineInterfacePlan commandLineInterfacePlan;
    private final CommandLineInterfaceActivity commandLineInterfaceActivity;
    private final Settler settler;

    private DependencyInjector() {

        this.view = new View();

        this.userRepository = new UserRepositoryMap();
        this.activityRepository = new ActivityRepositoryMap();
        this.planRepository = new PlanRepositoryMap();

        this.userService = new UserService(this.userRepository, this.planRepository);
        this.activityService = new ActivityService(this.activityRepository);
        this.planService = new PlanService(this.planRepository, this.userRepository, this.activityRepository);

        this.settler = new Settler(this.activityService, this.planService, this.userService);
        this.commandLineInterfaceUser = new CommandLineInterfaceUser(this.userService, this.view);
        this.commandLineInterfacePlan = new CommandLineInterfacePlan(this.planService, this.view);
        this.commandLineInterfaceActivity = new CommandLineInterfaceActivity(this.activityService, this.view);

        this.commandLineInterface = new CommandLineInterface(this.userService, this.view, this.commandLineInterfaceUser, this.commandLineInterfacePlan, this.commandLineInterfaceActivity);

        this.errorHandler = new ErrorHandler(this.commandLineInterface, this.view);
    }

    public static DependencyInjector getDependencyInjector() {
        if (dependencyInjector == null) {
            dependencyInjector = new DependencyInjector();
        }
        return dependencyInjector;
    }

    public void run() {
        try {
            this.settler.run();
        } catch (Exception e) {
            this.view.showError("Error al ejecutar el Settler: " + e.getMessage());
        }
        this.errorHandler.handlesErrors();
    }
}