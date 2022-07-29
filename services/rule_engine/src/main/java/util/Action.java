package util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;


@JsonDeserialize(using=ActionDeserializer.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Action {
    private String name;

    public abstract void act(Object value);
}
