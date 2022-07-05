package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import lombok.*;

@Getter
@Setter
public class Homesmart extends Agent {

    public Homesmart() {
    }

    public Homesmart(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }


    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }
}