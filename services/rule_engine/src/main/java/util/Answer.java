package util;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    public enum Action {
        CALL_HITL,
        CALL_AGENT,
        NEXT_QUESTION,
        EXIT
    }

    private String text;
    private Action action;
}
