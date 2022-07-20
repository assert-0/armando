package agents;

import java.util.Date;
import java.util.HashMap;
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

import com.mindsmiths.armory.ArmoryAPI;

import com.mindsmiths.armory.components.*;
import com.mindsmiths.armory.templates.GenericInterface;

import signals.UserIdSignal;
import util.QuestionFactory;
import util.RealEstate;


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
    private static List<RealEstate> reImages = new ArrayList<RealEstate>();
    static {
        reImages.add(new RealEstate("https://images.adsttc.com/media/images/629f/3517/c372/5201/650f/1c7f/large_jpg/hyde-park-house-robeson-architects_1.jpg?1654601149", "Poseidon Villa", "1 000 000 EUR"));
        reImages.add(new RealEstate("https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2020/02/home-banner-2020-02-min.jpg", "Family Palmatin House", "300 000 EUR"));
        reImages.add(new RealEstate("https://www.croatialuxuryrent.com/storage/upload/60a/bf3/6be/IMG_5654_tn.jpg", "Modern Villa", "2 200 000 EUR"));
    }
    private int reIndex = 1;

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

    public void addConnection(String connectionName, String connectionId) {
        this.connections.put(connectionName, connectionId);
    }

    public void displayUI() {
        List<SubmitButton> btnList = new ArrayList<>();
        btnList.add(new SubmitButton("1", "Next", new HashMap()));
        btnList.add(new SubmitButton("2", "Previous", new HashMap()));
        GenericInterface ui = new GenericInterface(
            new Title(reImages.get(reIndex).getName()), 
            new Image(reImages.get(reIndex).getSrc()),
            null,
            new Description(String.format("Hello %s! This is our personalized selection of Real Estate for you! It can be yours for just %s!", user.getName(), reImages.get(reIndex).getPrice())),
            null,
            btnList,
            false
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
    }
}
