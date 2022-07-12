package classes;

import java.util.List;

import lombok.*;

@Data
public class Answer {
    private enum Action {
        CALL_HITL,
        CALL_AGENT,
        NEXT_QUESTION,
        EXIT
    }

    private String text;
    private Action action;
}
