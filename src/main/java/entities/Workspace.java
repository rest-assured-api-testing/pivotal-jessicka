package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Workspace {
    @JsonIgnore
    public String kind;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    public int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;
    @JsonIgnore
    public int person_id;
    @JsonIgnore
    public List<Integer> project_ids;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public List<Integer> getProject_ids() {
        return project_ids;
    }

    public void setProject_ids(List<Integer> project_ids) {
        this.project_ids = project_ids;
    }
}
