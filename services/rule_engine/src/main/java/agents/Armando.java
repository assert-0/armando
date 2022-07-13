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
    public static List<Question> YesQuestions = new ArrayList<>();
    public static List<Question> NoQuestions = new ArrayList<>();

    static {
        QuestionFactory.fillQuestions(YesQuestions, "YES");
        QuestionFactory.fillQuestions(NoQuestions, "NO");
    }
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();
    private List<Question> questions;
    private int currentIndex = -1;

    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        this.userId = userId;
    }

    public void setUser(User user) {
        this.info("Setting user: " + user);
        this.user = user;
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
                    .map(answer -> new KeyboardOption(answer.getText(), answer.getText()))
                    .toList(),
                false,
                true
            )
        );
    }

    public void sendNextQuestion() {
        currentIndex++;
        if (currentIndex < questions.size()) {
            sendQuestion(questions.get(currentIndex));
        }
    }

    public void sendInterestQuestionare() {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "Is everything okay with your real estate?\n",
            new KeyboardData(
                "5982093762831",
                Arrays.asList(
                    new KeyboardOption("YES", "YES"),
                    new KeyboardOption("NO", "NO")
                )
            )
        );
    }

    public void handleAnswer(List<String> answers) {
        UserIdSignal signal = new UserIdSignal();
        signal.setUserId(getUserId());
        boolean callHitlFlag = false;
        boolean callAgentFlag = false;
        boolean nextQuestionFlag = false;
        for (var answer : answers) {
            for (var questionAnswer : questions.get(currentIndex).getAnswers()) {
                this.info("Answer: " + answer + ", QuestionAnswer: " + questionAnswer);
                if (answer.equals(questionAnswer.getText())) {
                    switch (questionAnswer.getAction()) {
                        case CALL_HITL:
                            callHitlFlag = true;
                            break;
                        case CALL_AGENT:
                            callAgentFlag = true;
                            break;
                        case NEXT_QUESTION:
                            nextQuestionFlag = true;
                            break;
                        case EXIT:
                            break;
                    }
                }
            }
        }
        if(callHitlFlag) send("HITL", signal);
        if(callAgentFlag) send("AGENT", signal);
        if(nextQuestionFlag) sendNextQuestion();
        DBAdapterAPI.updateUser(user);
    }

    public void handleFetchResult(String answer) {
        user.setInterested(answer.equals("YES"));
        DBAdapterAPI.updateUser(user);
        sendNextQuestion();
    }

    public static void info(String message) {
        Log.LOGGER.info(message);
    }
}