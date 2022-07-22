package agents;

import java.util.Arrays;
import java.util.Date;
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

import signals.UserIdSignal;
import util.QuestionFactory;
import util.Question;
import util.Action;


@Getter
@Setter
@NoArgsConstructor
public class Armando extends Agent {
    private String userId;
    private User user;
    private Date lastInteractionTime = new Date();
    private Stack<Question> questions = QuestionFactory.createShuffledConversation(
        "Hej Gita, znaš li da je vrijednost nekretnina na Medveščaku "
        + "narasla za 10% u zadnja 3 mjeseca? Trendove možeš proučiti ovdje: "
        + "[www.armando.com/korisne-statistike/cijena](www.armando.com/korisne-statistike/cijena)",
        "Na Ilici će se renovirati prometne trake u smjeru istoka idući "
        + "tjedan! Više informacija o tome: "
        + "[www.armando.com/zagreb/radovi/ilica](www.armando.com/zagreb/radovi/ilica)",
        "Vjerojatno znaš, ali u slučaju da ne, u tvom stambenom kompleksu je stan "
        + "nedavno stavljen na prodaju? Više informacija možeš saznati na: "
        + "[www.armando.com/zagreb/medvescak/prodaja](www.armando.com/zagreb/medvescak/prodaja)",
        "Hej, imam super vijesti za Medveščak, do proljeća ćeš imati novi park, "
        + "a samim time i vrijedniju nekretninu :) Gdje se park nalazi i kako "
        + "će izgledati možeš saznati ovdje: "
        + "[www.armando.com/zagreb/novi-projekti](www.armando.com/zagreb/novi-projekti)",
        "Čisto informativno, na području Medveščaka se mijenja toplovod! "
        + "Tvoj kvart neće imati vode preksutra od 16-20. Više o tome na linku: "
        + "[www.armando.com/zagreb/novi-projekti/infrastruktura](www.armando.com/zagreb/novi-projekti/infrastruktura)"
    );

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
        if (question.getAnswers().size() == 0) {
            TelegramAdapterAPI.sendMessage(
                connections.get("telegram"),
                question.getText()
            );
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
}
