package util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private String text;
    private Action action;
    private Question nextQuestion;
}
