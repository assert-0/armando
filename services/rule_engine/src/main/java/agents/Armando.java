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
        QuestionFactory.createShuffledConversation(
            "Hej ${user.name}, znaš li da je vrijednost nekretnina na Medveščaku "
            + "narasla za 10% u zadnja 3 mjeseca? Trendove možeš proučiti ovdje: "
            + "[${armoryUrl}](${armoryUrl})",
            "Na Ilici će se renovirati prometne trake u smjeru istoka idući "
            + "tjedan! Više informacija o tome: "
            + "[${armoryUrl}](${armoryUrl})",
            "Vjerojatno znaš, ali u slučaju da ne, u tvom stambenom kompleksu je stan "
            + "nedavno stavljen na prodaju? Više informacija možeš saznati na: "
            + "[${armoryUrl}](${armoryUrl})",
            "Hej, imam super vijesti za Medveščak, do proljeća ćeš imati novi park, "
            + "a samim time i vrijedniju nekretninu :) Gdje se park nalazi i kako "
            + "će izgledati možeš saznati ovdje: "
            + "[${armoryUrl}](${armoryUrl})",
            "Čisto informativno, na području Medveščaka se mijenja toplovod! "
            + "Tvoj kvart neće imati vode preksutra od 16-20. Više o tome na linku: "
            + "[${armoryUrl}](${armoryUrl})"
        ),
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
    private int reIndex = 1;
    private int openedLinks = 0;
    private int sentLinks = 0;


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
            new Title("'Feng Shui' park dolazi na Maksimir!"), 
            null, // new Image("https://park-maksimir.hr/wp-content/uploads/2019/08/Mallinov-park-14.jpg"),
            new Description(String.format("%s, izuzev Pelješkog mosta, istočni stil gradnje dolazi i na zelene površine. Kod Ulice Izmišljene 13., na 5 minuta od tvog stana, Huawei je odlučio izgraditi tehnološki Feng Shui park za mlade koji uključuje solarne klupe, automatske LED lampe i novi model sigurnih tobogana. Ovaj park će značajno povećati vrijednost obližnjih nekretnina za barem ...", user.getName())),
            new Title("Dostupni alati"), 
            Arrays.asList(new SubmitButton("procjena", "Zatraži procjenu agenta!", new HashMap()),
                        new SubmitButton("kupnja", "Želim kupiti nekretninu", new HashMap()),
                        new SubmitButton("prodaja", "Želim prodati nekretninu", new HashMap())),
            "data:image/png;base64, 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"
        );
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
    }

    public void displayUI(String template) {
        RateInterface ui = new RateInterface(
            new Title("Upiši adresu!"),
            Arrays.asList(new SubmitButton("getrating", "Nazad", new HashMap()))
        );
       
        ArmoryAPI.updateTemplate(this.connections.get("armory"), "ref", ui);
        Log.info("Updated rating ui");
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
