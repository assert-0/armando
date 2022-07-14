package agents;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import lombok.*;

import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.dbAdapter.Question;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.KeyboardOption;

import signals.UserIdSignal;
import classes.QuestionFactory;


@Getter
@Setter
@NoArgsConstructor
public class Armando extends Agent {
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();
    private classes.Question currentQuestion = QuestionFactory.getQuestionTree();

    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        this.userId = userId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendQuestion(classes.Question question) {
        if (question == null) return;
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            question.getText(),
            new KeyboardData(
                question.getId(),
                question.getAnswers()
                    .stream()
                    .map(answer -> new KeyboardOption(answer.getText(), answer.getText()))
                    .toList(),
                false,
                question.isMultiple()
            )
        );
    }

    public void sendFirstQuestion() {
        user.getQuestions().clear();
        DBAdapterAPI.updateUser(user);
        sendQuestion(currentQuestion);
    }

    public void handleAnswer(List<String> answers) {
        UserIdSignal signal = new UserIdSignal();
        signal.setUserId(getUserId());
        boolean callHitlFlag = false;
        boolean callAgentFlag = false;
        classes.Question nextQuestion = null;
        for (var answer : answers) {
            for (var questionAnswer : currentQuestion.getAnswers()) {
                if (answer.equals(questionAnswer.getText())) {
                    switch (questionAnswer.getAction()) {
                        case CALL_HITL:
                            callHitlFlag = true;
                            break;
                        case CALL_AGENT:
                            callAgentFlag = true;
                            break;
                        case NONE:
                            break;
                    }
                    nextQuestion = questionAnswer.getNextQuestion();
                }
            }
        }
        if (callHitlFlag) send("HITL", signal);
        if (callAgentFlag) send("AGENT", signal);
        user.getQuestions().add(new Question(currentQuestion.getText(), answers));
        DBAdapterAPI.updateUser(user);
        currentQuestion = nextQuestion;
        sendQuestion(nextQuestion);
    }
}
