package classes;

import java.util.List;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    public enum Action {
        CALL_HITL,
        CALL_AGENT,
        NONE
    }

    private String text;
    private Action action;
    private Question nextQuestion;
}
