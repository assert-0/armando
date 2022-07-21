package com.mindsmiths.mitems;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Element implements Serializable {
    private String id;
    private String description;
    private String slug;
    private ElementType type;
    private String content;
    private Integer index;
    private String itemId;
}
