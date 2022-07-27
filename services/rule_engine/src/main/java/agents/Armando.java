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
            new Image("https://park-maksimir.hr/wp-content/uploads/2019/08/Mallinov-park-14.jpg"),
            new Description(String.format("%s, izuzev Pelješkog mosta, istočni stil gradnje dolazi i na zelene površine. Kod Ulice Izmišljene 13., na 5 minuta od tvog stana, Huawei je odlučio izgraditi tehnološki Feng Shui park za mlade koji uključuje solarne klupe, automatske LED lampe i novi model sigurnih tobogana. Ovaj park će značajno povećati vrijednost obližnjih nekretnina za barem ...", user.getName())),
            new Title("Dostupni alati"), 
            Arrays.asList(new SubmitButton("procjena", "Zatraži procjenu agenta!", new HashMap()),
                        new SubmitButton("kupnja", "Želim kupiti nekretninu", new HashMap()),
                        new SubmitButton("prodaja", "Želim prodati nekretninu", new HashMap()))
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
