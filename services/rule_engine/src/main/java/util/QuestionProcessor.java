package util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.*;


@JsonDeserialize(using=QuestionProcessorDeserializer.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class QuestionProcessor {
    private String name;

    public abstract Question process(Question question, Object value);
}
