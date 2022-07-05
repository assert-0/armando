package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import java.util.Date;
import lombok.*;

@Getter
@Setter
public class Homesmart extends Agent {
    private Date lastInteractionTime;

    public Homesmart() {
        lastInteractionTime = new Date();
    }

    public Homesmart(String connectionName, String connectionId) {
        super(connectionName, connectionId);
        lastInteractionTime = new Date();
    }


    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }
}