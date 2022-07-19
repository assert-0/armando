package com.mindsmiths.armory.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mindsmiths.sdk.core.db.DataModel;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DataModel(serviceName = "armory")
public class SubmitEvent extends ArmoryEvent {
    protected String referenceId;
    protected ObjectNode data;
}
