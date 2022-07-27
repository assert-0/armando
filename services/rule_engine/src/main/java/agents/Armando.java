package agents;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.Question;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.dbAdapter.Activity;

import com.mindsmiths.armory.ArmoryAPI;

import com.mindsmiths.armory.components.*;
import com.mindsmiths.armory.templates.GenericInterface;
import com.mindsmiths.armory.templates.BaseTemplate;

import signals.UserIdSignal;
import util.QuestionFactory;
import util.QuestionHandler;
import util.processors.TemplateQuestionProcessor;
import util.RealEstate;
import util.armory.DisplayInterface;
import util.armory.RateInterface;


@Getter
@Setter
@NoArgsConstructor
public class Armando extends AbstractAgent {
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();
    private QuestionHandler handler = new QuestionHandler(
        QuestionFactory.createConversation(),
        new TemplateQuestionProcessor(Armando.class)
    );
    private static List<RealEstate> reImages = new ArrayList<RealEstate>();
    static {
        reImages.add(new RealEstate("https://images.adsttc.com/media/images/629f/3517/c372/5201/650f/1c7f/large_jpg/hyde-park-house-robeson-architects_1.jpg?1654601149", "Poseidon Villa", "1 000 000 EUR"));
        reImages.add(new RealEstate("https://q4g9y5a8.rocketcdn.me/wp-content/uploads/2020/02/home-banner-2020-02-min.jpg", "Family Palmatin House", "300 000 EUR"));
        reImages.add(new RealEstate("https://www.croatialuxuryrent.com/storage/upload/60a/bf3/6be/IMG_5654_tn.jpg", "Modern Villa", "2 200 000 EUR"));
        reImages.add(new RealEstate("https://images.unsplash.com/photo-1564013799919-ab600027ffc6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YmVhdXRpZnVsJTIwaG91c2V8ZW58MHx8MHx8&w=1000&q=80", "Modern Pool Family House", "500 000 EUR"));
        reImages.add(new RealEstate("https://www.forbes.com/advisor/wp-content/uploads/2022/03/houses_expensive.jpg", "Secluded House", "450 000 EUR"));
        reImages.add(new RealEstate("https://www.mcdonaldjoneshomes.com.au/sites/default/files/styles/image_gallery/public/daytona-new-house-designs.jpg?itok=Bb9xYGdE", "Cosy Family House", "200 000 EUR"));
    }
    private String articleTitle;
    private String articleText;

    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        this.userId = userId;
    }

    public String getArmoryUrl() {
        return "https://" + System.getenv("ARMORY_SITE_URL") + "/" + getConnection("telegram");
    }

    public void sendQuestion() {
        var question = handler.getCurrentProcessedQuestion(this);
        if (question == null) return;
        if (question.getAnswers().size() == 0) {
            TelegramAdapterAPI.sendMessage(
                connections.get("telegram"),
                question.getText()
            );
            handler.nextQuestion();
        }
        else {
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
    }

    public <T extends Agent> void contactAgent(Class<T> agentClass) {
        UserIdSignal signal = new UserIdSignal();
        signal.setUserId(getUserId());
        sendFirst(agentClass, signal);
    }

    public void sendFirstQuestion() {
        user.getQuestions().clear();
        DBAdapterAPI.updateUser(user);
        sendQuestion();
    }

    public void handleAnswer(List<String> answers) {
        var question = handler.getCurrentQuestion();
        user.getQuestions().add(new Question(question.getText(), answers));
        DBAdapterAPI.updateUser(user);
        handler.submitAnswersAndAct(answers, this);
        sendQuestion();
    }

    public void displayUI() {
        DisplayInterface ui = new DisplayInterface(
            new Title(this.articleTitle), 
            null, // new Image("https://park-maksimir.hr/wp-content/uploads/2019/08/Mallinov-park-14.jpg"),
            new Description(this.articleText),
            new Title("Dostupni alati"), 
            Arrays.asList(new SubmitButton("procjena", "Zatraži procjenu agenta!", new HashMap()),
                        new SubmitButton("kupnja", "Želim kupiti nekretninu", new HashMap()),
                        new SubmitButton("prodaja", "Želim prodati nekretninu", new HashMap())),
            "data:image/png;base64,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"
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
    }

    public void displayUI(String base64String) {
        DisplayInterface ui = new DisplayInterface(
            new Title(this.articleTitle), 
            null, // new Image("https://park-maksimir.hr/wp-content/uploads/2019/08/Mallinov-park-14.jpg"),
            new Description(this.articleText),
            new Title("Dostupni alati"), 
            Arrays.asList(new SubmitButton("procjena", "Zatraži procjenu agenta!", new HashMap()),
                        new SubmitButton("kupnja", "Želim kupiti nekretninu", new HashMap()),
                        new SubmitButton("prodaja", "Želim prodati nekretninu", new HashMap())),
            "data:image/png;base64," + base64String
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
        Log.warn("Updated new ui");
    }

    public void handleSignalResponse(String signalName) {
        switch (signalName) {
            case "procjena": 
                this.user.getActivities().add(new Activity(Activity.Type.APPRAISAL_SIGNAL, new Date()));
                break;
            case "kupnja": 
                this.user.getActivities().add(new Activity(Activity.Type.PURCHASE_SIGNAL, new Date()));
                break;
            case "prodaja": 
                this.user.getActivities().add(new Activity(Activity.Type.SELLING_SIGNAL, new Date()));
                break;
        }
        DBAdapterAPI.updateUser(user);
        Log.info("UPDATED USER: " + user);
    }
}
