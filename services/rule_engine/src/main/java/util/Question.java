package util;

import java.util.List;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private String text;
    private boolean multiple;
    private List<Answer> answers;
}
