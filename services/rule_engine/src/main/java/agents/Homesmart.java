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
public class Homesmart extends Agent {
    private Date lastInteractionTime;
    private String customerAnswer;
    private String userId;

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

    public void sendInterestQuestionare() {
        TelegramAdapterAPI.sendMessage(
            connections.get("telegram"),
            "Are you interested in a purchase of Real Estate?\n",
            new KeyboardData(
                "5982093762831",
                Arrays.asList(
                    new KeyboardOption("YES", "YES"),
                    new KeyboardOption("NO", "NO")
                )
            )
        );
    }


    // TODO: fix
    public void updateUserAnswer(Object obj1, Object obj2) {}

//    public void updateUserAnswer(User user, String answer) {
//        user.answer = answer;
//        DB_AdapterAPI.updateUser(user);
//    }
} 