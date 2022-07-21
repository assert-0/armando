package agents;

import java.util.Date;

import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.User;

import com.mindsmiths.armory.ArmoryAPI;

import com.mindsmiths.armory.components.*;
import com.mindsmiths.armory.templates.GenericInterface;

import signals.UserIdSignal;
import util.QuestionFactory;

import util.RealEstate;
import util.Question;
import util.Action;



@Getter
@Setter
@NoArgsConstructor
public class Armando extends Agent {
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();

    private int currentIndex = -1;
    private static List<RealEstate> reImages = new ArrayList<RealEstate>();
    static {
        reImages.add(new RealEstate("https://images.adsttc.com/media/images/629f/3517/c372/5201/650f/1c7f/large_jpg/hyde-park-house-robeson-architects_1.jpg?1654601149", "Poseidon Villa", "1 000 000 EUR"));
        reImages.add(new RealEstate("https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2020/02/home-banner-2020-02-min.jpg", "Family Palmatin House", "300 000 EUR"));
        reImages.add(new RealEstate("https://www.croatialuxuryrent.com/storage/upload/60a/bf3/6be/IMG_5654_tn.jpg", "Modern Villa", "2 200 000 EUR"));
    }
    private int reIndex = 1;

    private Stack<Question> questions = QuestionFactory.getQuestionTree();


    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        this.userId = userId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendQuestion() {
        Question question;
        try {
            question = questions.peek();
        } catch (EmptyStackException ignored) {
            return;
        }
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

    public void contactAgent(String agentId) {
        UserIdSignal signal = new UserIdSignal();
        signal.setUserId(getUserId());
        send(agentId, signal);
    }

    public void sendFirstQuestion() {
        user.getQuestions().clear();
        DBAdapterAPI.updateUser(user);
        sendQuestion();
    }

    public void handleAnswer(List<String> answers) {
        Question question = questions.pop();
        Set<Action> actions = new HashSet<>();
        for (var answer : answers) {
            for (var questionAnswer : question.getAnswers()) {
                if (answer.equals(questionAnswer.getText())) {
                    Question nextQuestion = questionAnswer.getNextQuestion();
                    if (nextQuestion != null) questions.push(nextQuestion);
                    Action action = questionAnswer.getAction();
                    if (action != null) actions.add(action);
                }
            }
        }
        for (var action : actions) {
            action.act(this);
        }
        user.getQuestions().add(new com.mindsmiths.dbAdapter.Question(question.getText(), answers));
        DBAdapterAPI.updateUser(user);
        sendQuestion();
    }
    public static void info(String message) {
        Log.LOGGER.info(message);
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
