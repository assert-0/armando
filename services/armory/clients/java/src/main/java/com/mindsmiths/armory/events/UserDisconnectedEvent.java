package com.mindsmiths.armory.events;

import com.mindsmiths.sdk.core.db.DataModel;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DataModel(serviceName = "armory")
public class UserDisconnectedEvent extends ArmoryEvent {
    protected String referenceId;
}
