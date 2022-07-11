package agents;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.User;


@Getter
@Setter
@NoArgsConstructor
public class AgentHitl extends AbstractAgent {
    public static String ID = "HITL";

    public AgentHitl(String connectionName, String connectionId) {
        super(connectionName, connectionId, ID);
    }

    public void sendSurvey(User user) {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "Why are they not interested?\n",
            new KeyboardData(
                user.getId(),
                Arrays.stream(User.NoInterestReason.values())
                    .map(value -> new KeyboardOption(value.name(), value.getText()))
                    .toList()
            )
        );
    }

    public static void handleFetchResult(User user, String answer) {
        user.setNoInterestReason(User.NoInterestReason.valueOf(answer));
        DBAdapterAPI.updateUser(user);
    }
}
