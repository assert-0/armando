package com.mindsmiths.armory.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArmoryEvent implements Serializable {
    protected String id;
    protected Date timestamp;
    protected String connectionId;
    protected String adapter;
    protected ObjectNode additionalData;
}
