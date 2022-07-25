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
import util.armory.DisplayInterface;
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
        reImages.add(new RealEstate("https://images.unsplash.com/photo-1564013799919-ab600027ffc6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YmVhdXRpZnVsJTIwaG91c2V8ZW58MHx8MHx8&w=1000&q=80", "Modern Pool Family House", "500 000 EUR"));
        reImages.add(new RealEstate("https://www.forbes.com/advisor/wp-content/uploads/2022/03/houses_expensive.jpg", "Secluded House", "450 000 EUR"));
        reImages.add(new RealEstate("https://www.mcdonaldjoneshomes.com.au/sites/default/files/styles/image_gallery/public/daytona-new-house-designs.jpg?itok=Bb9xYGdE", "Cosy Family House", "200 000 EUR"));
    }
    private int reIndex = 1;
    /* private static HashMap<Image, List<SubmitButton>> spRealEstates;
    private static HashMap<Image, List<SubmitButton>> slRealEstates;
    static {
        for (int i = 0; i < 2; ++i)
            spRealEstates.put(new Image(reImages.get(i).getSrc()), Arrays.asList(new SubmitButton(String.valueOf(i), "More info", new HashMap())));
        for (int i = 3; i < 6; ++i)
            slRealEstates.put(new Image(reImages.get(i - 3).getSrc()), Arrays.asList(new SubmitButton(String.valueOf(i - 3), "More info", new HashMap())));
    } */
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
        DisplayInterface ui = new DisplayInterface(
            new Title("Novi park!"), 
            new Image("https://park-maksimir.hr/wp-content/uploads/2019/08/Mallinov-park-14.jpg"),
            new Description(String.format("Hej %s, gradi se novi park na Ulica izmišljena 13! Planirani rok izgradnje je kraj 2022.", user.getName(), reImages.get(reIndex).getPrice())),
            new Title("Nekretnine u blizini"), 
            new Image(reImages.get(0).getSrc()),
            Arrays.asList(new SubmitButton("0", "More info", new HashMap())),
            new Image(reImages.get(1).getSrc()),
            Arrays.asList(new SubmitButton("1", "More info", new HashMap())),
            new Image(reImages.get(2).getSrc()),
            Arrays.asList(new SubmitButton("2", "More info", new HashMap())),
            new Title("Nekretnine sličnih cijena"), 
            new Image(reImages.get(3).getSrc()),
            Arrays.asList(new SubmitButton("3", "More info", new HashMap())),
            new Image(reImages.get(4).getSrc()),
            Arrays.asList(new SubmitButton("4", "More info", new HashMap())),
            new Image(reImages.get(5).getSrc()),
            Arrays.asList(new SubmitButton("5", "More info", new HashMap()))
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
    }

    public void displayUI(String idString) {
        int id = Integer.valueOf(idString);
        DisplayInterface ui = new DisplayInterface(
            new Title(reImages.get(id).getName()), 
            new Image(reImages.get(id).getSrc()),
            new Description(String.format("%s, ova nekretnina može biti tvoja za %s", user.getName(), reImages.get(id).getPrice())),
            new Title("Nekretnine u blizini"), 
            new Image(reImages.get(0).getSrc()),
            new ArrayList<>(Arrays.asList(new SubmitButton("0", "More info", new HashMap()))),
            new Image(reImages.get(1).getSrc()),
            new ArrayList<>(Arrays.asList(new SubmitButton("1", "More info", new HashMap()))),
            new Image(reImages.get(2).getSrc()),
            new ArrayList<>(Arrays.asList(new SubmitButton("2", "More info", new HashMap()))),
            new Title("Nekretnine sličnih cijena"), 
            new Image(reImages.get(3).getSrc()),
            new ArrayList<>(Arrays.asList(new SubmitButton("3", "More info", new HashMap()))),
            new Image(reImages.get(4).getSrc()),
            new ArrayList<>(Arrays.asList(new SubmitButton("4", "More info", new HashMap()))),
            new Image(reImages.get(5).getSrc()),
            new ArrayList<>(Arrays.asList(new SubmitButton("5", "More info", new HashMap())))
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
    }
}
