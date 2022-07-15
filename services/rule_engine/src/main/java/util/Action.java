package util;

import lombok.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import agents.Armando;


@JsonDeserialize(using = ActionDeserializer.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Action {
    private String name;

    public abstract void act(Armando agent);
}
