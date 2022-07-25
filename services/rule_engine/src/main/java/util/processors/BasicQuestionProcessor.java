package util.processors;

import lombok.*;

import util.Question;
import util.QuestionProcessor;


@Data
public class BasicQuestionProcessor extends QuestionProcessor {
    public static final String PROCESSOR_NAME = "BasicProcessor";

    public BasicQuestionProcessor() {
        super(PROCESSOR_NAME);
    }

    @Override
    public Question process(Question question, Object value) {
        return question;
    }
}
