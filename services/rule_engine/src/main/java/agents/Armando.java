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
import util.QuestionFactory;


@Getter
@Setter
@NoArgsConstructor
public class Armando extends Agent {
    public static List<util.Question> YesQuestions = new ArrayList<>();
    public static List<util.Question> NoQuestions = new ArrayList<>();

    public static String initialQuestion = "Is everything okay with your real estate?";

    static {
        YesQuestions = QuestionFactory.fillQuestions("YES");
        NoQuestions = QuestionFactory.fillQuestions("NO");
    }
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();
    private List<util.Question> questions;
    private int currentIndex = -1;

    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        this.userId = userId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendQuestion(util.Question question) {
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
            initialQuestion,
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
        user.getQuestions().add(new Question(questions.get(currentIndex).getText(), answers));
        DBAdapterAPI.updateUser(user);
        if (callHitlFlag) send("HITL", signal);
        if (callAgentFlag) send("AGENT", signal);
        if (nextQuestionFlag) sendNextQuestion();
    }

    public void updateUserInterest(String answer) {
        user.setInterested(answer.equals("YES"));
        user.getQuestions().clear();
        user.getQuestions().add(new Question(initialQuestion, Arrays.asList(answer)));
        DBAdapterAPI.updateUser(user);
    }

    public static void info(String message) {
        Log.LOGGER.info(message);
    }
}