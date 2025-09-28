package upm.data.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Plan {
    private static final Integer SIN_LIMITE = -1;
    private final String name;
    private final String place;
    private final Valuation rating;
    private final List<Activity> activityList;
    private final List<User> subscribedUsers;
    private final List<User> usersThatRated;
    private User propietary;
    private String description;
    private LocalDateTime dateTime;
    private Integer capacity, vacantPositions;
    private Integer id;

    public Plan(String name, String description, LocalDateTime dateTime, String place, Integer capacity) {//poner en el CLI -1 en Integer aforo
        this.name = name;
        this.description = description;
        this.subscribedUsers = new ArrayList<>();
        this.usersThatRated = new ArrayList<>();
        this.setDateTime(dateTime);
        this.place = place;
        this.setCapacity(capacity);
        this.rating = new Valuation();
        this.vacantPositions = this.capacity;
        this.activityList = new ArrayList<>();
    }

    public User getPropietary() {
        return propietary;
    }

    public void setPropietary(User propietary) {
        this.propietary = propietary;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPlace() {
        return place;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        if (capacity < SIN_LIMITE)
            throw new InvalidAttributeException("El aforo es en positivo");
        this.capacity = capacity;
    }

    public Integer getVacantPositions() {
        return vacantPositions;
    }

    public void setVacantPositions(Integer vacantPositions) {
        this.vacantPositions = vacantPositions;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public List<User> getSubscribedUsers() {
        return subscribedUsers;
    }

    public List<User> getUsersThatRated() {
        return usersThatRated;
    }

    public Valuation getRating() {
        return rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalTime() {
        Integer total = 0;
        int comienzo = 1;
        for (Activity activity : this.activityList) {
            total += activity.getLengthMin();
            if (comienzo < this.activityList.size()) {
                total += 20;
            }
            comienzo++;
        }
        return total;
    }

    public BigDecimal getGenericTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (Activity activity : this.activityList) {
            total = total.add(activity.getCost());
        }
        return total;
    }

    public BigDecimal getDiscountedTotalPrice(int userAge) {
        BigDecimal total = BigDecimal.ZERO;
        for (Activity activity : this.activityList) {
            total = total.add(activity.applyDiscount(userAge));
        }
        return total;
    }

    public LocalDateTime getEndDatePlan() {
        return this.dateTime.plusMinutes(this.getTotalTime());
    }

    public double getAverageRating() {
        return this.rating.getAverageRating();
    }

    public void addActivity(Activity activity, User user) {
        if (this.propietary != user) {
            throw new InvalidAttributeException("No puedes añadir una actividad si no eres el propietario: " + user);
        }
        this.activityList.add(activity);
        this.modifyVacantPositionAndCapacity(activity);
    }

    private void modifyVacantPositionAndCapacity(Activity activity) {
        if ((activity.getCapacity() < this.capacity && activity.getCapacity() > SIN_LIMITE) || (activity.getCapacity() > this.capacity && this.capacity.equals(SIN_LIMITE))) {
            setVacantPositions(activity.getCapacity() - (this.capacity - this.vacantPositions));
            setCapacity(activity.getCapacity());
        }
    }

    public void subscribeUserToPlan(User user) {
        if (this.vacantPositions == 0) {
            throw new InvalidAttributeException("No te puedes suscribir, no quedan puestos vacantes: " + user);
        }
        this.subscribedUsers.add(user);
        if (this.vacantPositions > 0) {
            this.vacantPositions--;
        }
    }

    public void unsubscribeUserToPlan(User user) {
        this.subscribedUsers.remove(user);
        if (this.vacantPositions >= 0)
            this.vacantPositions++;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "propietary=" + propietary +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", place='" + place + '\'' +
                ", capacity=" + capacity +
                ", vacantPositions=" + vacantPositions +
                ", activityList=" + activityList +
                ", subscribedUsers=" + subscribedUsers +
                ", usersThatRated=" + usersThatRated +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(name, plan.name) && Objects.equals(place, plan.place) && Objects.equals(rating, plan.rating) && Objects.equals(activityList, plan.activityList) && Objects.equals(subscribedUsers, plan.subscribedUsers) && Objects.equals(usersThatRated, plan.usersThatRated) && Objects.equals(propietary, plan.propietary) && Objects.equals(description, plan.description) && Objects.equals(dateTime, plan.dateTime) && Objects.equals(capacity, plan.capacity) && Objects.equals(vacantPositions, plan.vacantPositions) && Objects.equals(id, plan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
