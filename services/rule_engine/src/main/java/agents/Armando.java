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
    private static HashMap<String, String> activityMessages = new HashMap<>(); 
    static {
        //activityMessages.put(key, value);
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
            null,
            new Description(this.articleText),
            Arrays.asList(new SubmitButton("procjena", "Zatraži procjenu agenta!", new HashMap()),
                        new SubmitButton("kupnja", "Želim kupiti nekretninu", new HashMap()),
                        new SubmitButton("prodaja", "Želim prodati nekretninu", new HashMap())),
            null
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
    }

    public void displayUI(String base64String) {
        DisplayInterface ui = new DisplayInterface(
            new Title(this.articleTitle), 
            null,
            new Description(this.articleText),
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
