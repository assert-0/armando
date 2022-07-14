package util;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String text;
    private List<Answer> answers;
}
