package agents;

import java.util.Date;
import java.util.Arrays;

import lombok.*;

import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;


@Getter
@Setter
public abstract class AbstractAgent extends Agent {

    protected AbstractAgent() {
    }

    protected AbstractAgent(String connectionName, String connectionId, String agentId) {
        super(connectionName, connectionId);
        id = agentId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public void sendContactInfo(User user) {
        sendMessage("Your client's name and surname: " + user.getName() + " " + user.getSurname() 
        + "\nYour client's contact:" + user.getPhoneNumber());
    }

    public abstract void sendSurvey();

    
}
