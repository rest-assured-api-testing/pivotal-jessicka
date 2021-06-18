package entities;

public enum CurrentState {
    ACCEPTED("accepted"), DELIVERED("delivered"), FINISHED("finished"),
    STARTED("started"), REJECTED("rejected"), PLANNED("planned"), UNSTARTED("unstarted"), UNSCHEDULED("unscheduled");

    private String currentState;

    CurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
