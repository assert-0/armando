package util;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private String text;
    @JsonDeserialize(as = CallAgentAction.class)
    private Action action;
    private Question nextQuestion;
}
