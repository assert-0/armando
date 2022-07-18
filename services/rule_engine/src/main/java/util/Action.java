package util;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import agents.Armando;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "class"
)
@JsonSubTypes({
    @JsonSubTypes.Type(name = "CallAgent", value = CallAgentAction.class),
    @JsonSubTypes.Type(name = "SendMessage", value = SendMessageAction.class)
})
public abstract class Action {
    public abstract void act(Armando agent);
}
