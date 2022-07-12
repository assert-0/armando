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


@Getter
@Setter
@NoArgsConstructor
public class Armando extends Agent {
    private static List<Question> YesQuestions = new ArrayList<>();
    private static List<Question> NoQuestions = new ArrayList<>();

    static {
        YesQuestions.add(
            new Question(
                "Glad to hear that! What exactly do you like with your new real estate?",
                Arrays.asList(
                    new Answer("The amount of space!", Answer.Action.NEXT_QUESTION),
                    new Answer("The beautiful balcony!", Answer.Action.NEXT_QUESTION),
                    new Answer("The great location!", Answer.Action.NEXT_QUESTION),
                    new Answer("The construction year!", Answer.Action.NEXT_QUESTION),
                    new Answer("The elevator!", Answer.Action.NEXT_QUESTION),
                    new Answer("The housing infrastructure that comes with it!", Answer.Action.NEXT_QUESTION),
                    new Answer("The civil infrastructure!", Answer.Action.NEXT_QUESTION),
                    new Answer("The fact that it was furnished already!", Answer.Action.NEXT_QUESTION),
                    new Answer("The number of rooms!", Answer.Action.NEXT_QUESTION),
                    new Answer("Something else!", Answer.Action.CALL_HITL)
                )
            )
        );
        YesQuestions.add(
            new Question(
                "Were there maybe some difficulties that you discovered with the real estate?",
                Arrays.asList(
                    new Answer("Too much/too little space...", Answer.Action.NEXT_QUESTION),
                    new Answer("The balcony or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The not ideal location...", Answer.Action.NEXT_QUESTION),
                    new Answer("The construction year...", Answer.Action.NEXT_QUESTION),
                    new Answer("The elevator or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The bad housing infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The bad infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The furniture or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("Too many/too few rooms..." , Answer.Action.NEXT_QUESTION),
                    new Answer("The neighbours...", Answer.Action.NEXT_QUESTION),
                    new Answer("Something else...", Answer.Action.CALL_HITL)
                )
            )
        );        
        YesQuestions.add(
            new Question(
                "Yikes, maybe I can help with that! Are you perhaps interested in buying a new real estate?",
                Arrays.asList(
                    new Answer("Sure!", Answer.Action.CALL_AGENT),
                    new Answer("Not really, no.", Answer.Action.CALL_HITL)
                )
            )
        );
        NoQuestions.add(
            new Question(
                "Sorry to hear that :/ What exactly is bothering you with your current real estate?",
                Arrays.asList(
                    new Answer("Too much/too little space...", Answer.Action.NEXT_QUESTION),
                    new Answer("The balcony or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The not ideal location...", Answer.Action.NEXT_QUESTION),
                    new Answer("The construction year...", Answer.Action.NEXT_QUESTION),
                    new Answer("The elevator or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The bad housing infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The bad infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("The furniture or lack of it...", Answer.Action.NEXT_QUESTION),
                    new Answer("Too many/too few rooms..." , Answer.Action.NEXT_QUESTION),
                    new Answer("The neighbours...", Answer.Action.NEXT_QUESTION),
                    new Answer("Something else...", Answer.Action.CALL_HITL)
                )
            )
        );
        NoQuestions.add(
            new Question(
                "Yikes, maybe I can help with that! Are you perhaps interested in buying a new real estate?",
                Arrays.asList(
                    new Answer("Sure!", Answer.Action.CALL_AGENT),
                    new Answer("Not really, no.", Answer.Action.CALL_HITL)
                )
            )
        );
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
                    .map(answer => new KeyboardOption(answer.getText(), answer.getText())),
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
