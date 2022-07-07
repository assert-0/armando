package agents;

import java.util.Date;
import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.dbAdapter.DBAdapterAPI;


@Getter
@Setter
public abstract class AbstractAgent extends Agent{
    private String userId;

    public AbstractAgent(String connectionName, String connectionId, String agentId) {
        super(connectionName, connectionId);
        id = agentId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendContactInfo() {
        sendMessage("Your client's name and contact info is as follows:\n");
        DBAdapterAPI.fetchUser(userId);


    }

    public abstract void sendSurvey();

    
}
