package util;

import java.util.List;
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionHandler {
    private Stack<Question> questions;

    public Question getCurrentQuestion() {
        try {
            return questions.peek();
        } catch (EmptyStackException ignored) {
            return null;
        }
    }

    public void submitAnswersAndAct(List<String> answers, Object value) {
        Question question = questions.pop();
        Set<Action> actions = new HashSet<>();
        for (var answer : answers) {
            for (var questionAnswer : question.getAnswers()) {
                if (answer.equals(questionAnswer.getText())) {
                    var nextQuestion = questionAnswer.getNextQuestion();
                    if (nextQuestion != null) questions.push(nextQuestion);
                    var action = questionAnswer.getAction();
                    if (action != null) actions.add(action);
                }
            }
        }
        for (var action : actions) {
            action.act(value);
        }
    }
}
