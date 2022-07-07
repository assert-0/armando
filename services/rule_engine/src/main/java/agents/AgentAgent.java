package agents;

import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;


@Getter
@Setter
public class AgentAgent extends AbstractAgent{

    public AgentAgent(String connectionName, String connectionId, String clientId) {
        super(connectionName, connectionId, clientId);
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
}
