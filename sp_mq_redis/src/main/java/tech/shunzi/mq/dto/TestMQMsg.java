package tech.shunzi.mq.dto;

import java.io.Serializable;

/**
 * @author I348910
 */
public class TestMQMsg implements Serializable {



    private String guid;
    private String name;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
