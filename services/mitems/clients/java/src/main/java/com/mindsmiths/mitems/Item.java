package com.mindsmiths.mitems;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class Item implements Serializable {
    private String id;
    private String name;
    private String slug;
    private String flowId;
    private List<Element> elements;
}
