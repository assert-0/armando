package classes;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
public class Question {
    private String text;
    private List<Answer> answers;
}
