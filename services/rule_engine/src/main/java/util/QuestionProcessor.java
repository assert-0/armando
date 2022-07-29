package util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;


@JsonDeserialize(using=QuestionProcessorDeserializer.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class QuestionProcessor {
    private String name;

    public abstract Question process(Question question, Object value);
}
