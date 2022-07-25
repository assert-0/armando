package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Serializable {
    public enum Type {
        ARMORY_LINK
    }

    private Type type;
    private Date datetime;
}
