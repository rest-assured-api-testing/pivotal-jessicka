package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

public class Story {
    @JsonIgnore
    public String kind;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int id;
    @JsonIgnore
    public Date created_at;
    @JsonIgnore
    public Date updated_at;
    @JsonIgnore
    public StoryType story_type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;
    @JsonIgnore
    public CurrentState current_state;
    @JsonIgnore
    public int requested_by_id;
    @JsonIgnore
    public String url;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int project_id;
    @JsonIgnore
    public List<Integer> owner_ids;
    @JsonIgnore
    public List<Integer> labels;

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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public StoryType getStory_type() {
        return story_type;
    }

    public void setStory_type(StoryType story_type) {
        this.story_type = story_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentState getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(CurrentState current_state) {
        this.current_state = current_state;
    }

    public int getRequested_by_id() {
        return requested_by_id;
    }

    public void setRequested_by_id(int requested_by_id) {
        this.requested_by_id = requested_by_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public List<Integer> getOwner_ids() {
        return owner_ids;
    }

    public void setOwner_ids(List<Integer> owner_ids) {
        this.owner_ids = owner_ids;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }
}
