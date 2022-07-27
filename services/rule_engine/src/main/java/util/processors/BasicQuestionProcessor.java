package util.processors;

import lombok.*;

import util.Question;
import util.QuestionProcessor;


@Data
@NoArgsConstructor
public class BasicQuestionProcessor extends QuestionProcessor {
    @Override
    public Question process(Question question, Object value) {
        return question;
    }
}
