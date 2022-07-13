package agents;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import lombok.*;

import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.KeyboardOption;

import signals.UserIdSignal;
import classes.Question;
import classes.Answer;
import classes.QuestionFactory;


@Getter
@Setter
@NoArgsConstructor
public class Armando extends Agent {
    private static List<Question> YesQuestions = new ArrayList<>();
    private static List<Question> NoQuestions = new ArrayList<>();

    static {
        QuestionFactory.fillQuestions(YesQuestions, "YES");
        QuestionFactory.fillQuestions(NoQuestions, "NO");
    }
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();
    private List<Question> questions;
    private int currentIndex = 0;

    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        this.userId = userId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendQuestion(Question question) {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            question.getText(),
            new KeyboardData(
                "5982093762832",
                question.getAnswers()
                    .stream()
                    .map(answer -> new KeyboardOption(answer.getText(), answer.getText())),
                false,
                true
            )
        );
    }

    public void sendInterestQuestionare() {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "Are you interested in a purchase of Real Estate?\n",
            new KeyboardData(
                "5982093762831",
                Arrays.asList(
                    new KeyboardOption("YES", "YES"),
                    new KeyboardOption("NO", "NO")
                )
            )
        );
    }

    public void sendUserSignal(String answer) {
        UserIdSignal signal = new UserIdSignal();
        signal.setUserId(getUserId());
        if (answer.equals("YES")) {
            send("AGENT", signal, "signals");
            questions = YesQuestions;
        }
        else {
            send("HITL", signal, "signals");
            questions = NoQuestions;
        }
    }

    public void handleAnswer(List<String> answers) {
        for (var answer : answers) {
            for (var questionAnswer : questions.get(currentIndex).getAnswers()) {
                if (answer.equals(questionAnswer.getText())) {
                    switch (questionAnswer.getAction()) {
                        case Answer.Action.CALL_HITL:
                            break;
                        case Answer.Action.CALL_AGENT:
                            break;
                        case Answer.Action.NEXT_QUESTION:
                            break;
                        case Answer.Action.EXIT:
                            break;
                    }
                }
            }
        }
    }

    public void handleFetchResult(String answer) {
        user.setInterested(answer.equals("YES"));
        DBAdapterAPI.updateUser(user);
    }
}
