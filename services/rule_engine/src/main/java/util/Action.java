package util;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import agents.Armando;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class"
)
@JsonSubTypes(
    @JsonSubTypes.Type(CallAgentAction.class),
    @JsonSubTypes.Type(SendMessageAction.class)
)
public abstract class Action {
    public abstract void act(Armando agent);
}
