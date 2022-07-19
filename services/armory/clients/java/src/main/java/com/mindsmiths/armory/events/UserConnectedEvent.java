package com.mindsmiths.armory.events;

import com.mindsmiths.sdk.core.db.DataModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@DataModel(serviceName = "armory")
public class UserConnectedEvent extends ArmoryEvent {
}
