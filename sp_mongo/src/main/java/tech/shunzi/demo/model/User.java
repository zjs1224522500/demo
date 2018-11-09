package tech.shunzi.demo.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author I348910
 */
public class User implements Serializable {

    @Id
    private String id;
    private String userName;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
