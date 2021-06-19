package entities;

public enum StoryType {
    FEATURE("feature"), BUG("bug"), CHORE("chore"), RELEASE("release");

    private String storyType;

    StoryType(String storyType) {
        this.storyType = storyType;
    }

    public String getStoryType() {
        return storyType;
    }

    public void setStoryType(String storyType) {
        this.storyType = storyType;
    }
}
