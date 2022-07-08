package agents;

import java.util.Date;
import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;


@Getter
@Setter
public class ProtoAgent extends Agent {
    public ProtoAgent() {
    }

    public ProtoAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }

    @Override
    public String getConnection(String connectionName) {
        // TODO Auto-generated method stub
        return super.getConnection(connectionName);
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendRoleAssignment() {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "What is your role?\n",
            new KeyboardData(
                "5982093762831",
                Arrays.asList(
                    new KeyboardOption("USER", "User"),
                    new KeyboardOption("AGENT", "Agent"),
                    new KeyboardOption("HITL", "Hitl")
                )
            )
        );
    }
}
