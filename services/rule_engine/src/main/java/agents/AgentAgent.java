package agents;

import java.util.Arrays;

import lombok.*;

import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;


@Getter
@Setter
public class AgentAgent extends AbstractAgent {
    public static String ID = "AGENT";

    public AgentAgent() {
    }

    public AgentAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId, ID);
    }

    public void sendSurvey(){
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "They bought?\n",
            new KeyboardData(
                "5982093762831",
                Arrays.asList(
                    new KeyboardOption("YES", "YES"),
                    new KeyboardOption("NO", "NO")
                )
            )
        );
    }

    public static void handleFetchResult(User user, String answer) {
        user.setBought(answer.equals("YES"));
        DBAdapterAPI.updateUser(user);
    }
}
