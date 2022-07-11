package agents;

import java.util.Arrays;

import lombok.*;

import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.ruleEngine.util.Log;


@Getter
@Setter
@NoArgsConstructor
public class AgentAgent extends AbstractAgent {
    public static String ID = "AGENT";

    public AgentAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId, ID);
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
}
