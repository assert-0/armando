package util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.*;


@JsonSerialize(using=ClassNameSerializer.class)
@JsonDeserialize(using=ClassNameDeserializer.class)
@Data
@NoArgsConstructor
public abstract class QuestionProcessor {
    private final String ClassName = this.getClass().getName();

    public abstract Question process(Question question, Object value);
}
