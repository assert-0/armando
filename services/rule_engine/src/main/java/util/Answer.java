package util;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private String text;
    private Action action;
    private Question nextQuestion;
}
