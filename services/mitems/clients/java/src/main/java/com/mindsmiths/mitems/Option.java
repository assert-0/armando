package com.mindsmiths.mitems;

import lombok.Data;

import java.io.Serializable;

@Data
public class Option implements Serializable {
    private String id;
    private String text;
}
