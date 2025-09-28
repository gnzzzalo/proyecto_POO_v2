package upm.console;

public enum CommandNames {
    CREATE_USER("create_user", "create_user: <nombre>;<edad>;<movil>;<contraseña> Crea un usuario"),
    LOGIN("login", "login: <user>;<contraseña> Inicias sesión"),
    LOGOUT("logout", "logout: Cierras sesión"),
    CREATE_ACTIVITY("create_activity", "create_activity: <nombre>;<tipoDeActividad>;<descripcion>;<duracionMin>;<coste>;<aforo(opcional)> Crea una actividad"),
    CREATE_PLAN("create_plan", "create_plan: <nombre>;<descripcion>;<fechaYHora>;<lugar>;<aforo(opcional)> Crea un plan"),
    ADD_ACTIVITY_TO_PLAN("add_activity_to_plan", "add_activity_to_plan: <actividadId>;<planId> Añade una actividad a un plan propio"),
    DELETE_PLAN("delete_plan", "delete_plan: <planId> Elimina un plan propio"),
    TOTAL_TIME_PLAN("total_time_plan", "total_time_plan: <planId> Indica el tiempo total del plan"),
    SHOW_PRICE_PLAN("show_price_plan", "show_price_plan: <planId>Indica el precio del plan y si se ha aplicado descuento o no"),
    SHOW_FUTURE_PLANS("show_future_plans", "show_future_plans: Enseña los planes disponibles"),
    SHOW_FUTURE_PLANS_BY_RATE("show_future_plans_by_rate", "show_future_plans_by_rate: Enseña los planes disponibles ordenados según la puntuación media de planes antiguos del propietario"),
    SHOW_FUTURE_PLANS_BY_DATE("show_future_plans_by_date", "show_future_plans_by_date: Enseña los planes disponibles ordenados según la fecha"),
    SHOW_FUTURE_PLANS_BY_USER_NAME("show_future_plans_by_user_name", "show_future_plans_by_user_name: <userName> Enseña los planes futuros de un usuario (por nombre)"),
    SHOW_MOST_RATED_USER_FUTURE_PLANS("show_most_rated_user_future_plans", "show_most_rated_user_future_plans: Enseña los planes futuros del usuario con mejor valoración"),
    SHOW_PAST_PLANS_BY_LOGGED_USER("show_past_plans_by_logged_user", "show_past_plans_by_logged_user: Enseña los planes pasados de un usuario (por nombre)"),
    SUBSCRIBE_TO_PLAN("subscribe_to_plan", "subscribe_to_plan: <planId> Suscribe al usuario en un plan"),
    UNSUBSCRIBE_TO_PLAN("unsubscribe_to_plan", "unsubscribe_to_plan: <planId> Elimina la suscripción del usuario al plan"),
    SHOW_SUBSCRIBED_PLANS("show_subscribed_plans", "show_subscribed_plans: Enseña los planes a los que el usuario está suscrito"),
    HELP("help", "help: muestra la ayuda."),
    EXIT("exit", "exit: termina la ejecución.");

    private final String value;
    private final String help;

    CommandNames(String value, String help) {
        this.value = value;
        this.help = help;
    }

    public static CommandNames fromValue(String value) {
        for (CommandNames command : CommandNames.values()) {
            if (command.getValue().equals(value)) {
                return command;
            }
        }
        throw new UnsupportedOperationException("Comando '" + value + "' no existe.");
    }

    public String getValue() {
        return value;
    }

    public String getHelp() {
        return help;
    }
}
