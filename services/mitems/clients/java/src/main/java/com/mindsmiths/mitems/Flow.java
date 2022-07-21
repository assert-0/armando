package com.mindsmiths.mitems;

import com.mindsmiths.sdk.core.db.DataModel;
import com.mindsmiths.sdk.core.db.PrimaryKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@DataModel(serviceName = "mitems")
public class Flow implements Serializable {
    private String id;
    private String title;
    private String description;
    private String slug;
    private List<Item> items;
}
