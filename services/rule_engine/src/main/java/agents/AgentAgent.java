package agents;

import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.User;


@Getter
@Setter
@NoArgsConstructor
public class AgentAgent extends AbstractAgent {
    public AgentAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }

    public void sendSurvey(User user) {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "They bought?\n",
            new KeyboardData(
                user.getId(),
                Arrays.asList(
                    new KeyboardOption("YES", "YES"),
                    new KeyboardOption("NO", "NO")
                )
            )
        );
    }

    public static void handleFetchResult(User user, String answer) {
        user.setBoughtRE(answer.equals("YES"));
        Log.info("[UPDATE FROM METHOD] Update user bought: " + answer);
        DBAdapterAPI.updateUser(user);
    }

    public void sendContactInfo(User user) {
        sendMessage("Your client's name and surname: " + user.getName() + " " + user.getSurname()
        + "\nYour client's contact:" + user.getPhoneNumber());

        String msg = "";
        for (var question : user.getQuestions()) {
            msg += "\n[[BOT]]: " + question.getText() + "\n[[CUSTOMER]]: " + String.join(", ", question.getAnswers());
        }
        sendMessage(msg);
    }
}
