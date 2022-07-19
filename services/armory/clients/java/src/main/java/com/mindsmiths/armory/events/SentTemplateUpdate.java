package com.mindsmiths.armory.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mindsmiths.sdk.core.db.DataModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DataModel(serviceName = "armory")
public class SentTemplateUpdate implements Serializable {
    protected String id;
    protected String adapter;
    protected String connectionId;
    protected Date sentAt;
    protected String template;
    protected ObjectNode additionalData;
}
