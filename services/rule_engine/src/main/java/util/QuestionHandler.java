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
    private QuestionProcessor processor;

    public Question getCurrentQuestion() {
        try {
            var question = questions.peek();
            return question;
        } catch (EmptyStackException ignored) {
            return null;
        }
    }

    public Question getCurrentProcessedQuestion(Object processValue) {
        var question = getCurrentQuestion();
        if (question == null) return question;
        return processor.process(question, processValue);
    }

    public void nextQuestion() {
        questions.pop();
    }

    public void addQuestion(Question question) {
        questions.push(question);
    }

    public void submitAnswersAndAct(List<String> answers, Object actionValue) {
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
            action.act(actionValue);
        }
    }
}
