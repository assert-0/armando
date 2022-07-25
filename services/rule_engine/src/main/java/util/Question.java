package util;

import java.util.List;
import java.util.Collections;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private String text;
    private boolean multiple;
    private List<Answer> answers;

    public Question(String text) {
        this.id = null;
        this.text = text;
        this.multiple = false;
        this.answers = Collections.emptyList();
    }

    public Question(Question question) {
        this.id = question.id;
        this.text = question.text;
        this.multiple = question.multiple;
        this.answers = question.answers;
    }
}
