package entities;

public enum ProjectType {
    DEMO("demo"),PRIVATE("private"),PUBLIC("public"), SHARED("shared");

    private String projectType;

    ProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
