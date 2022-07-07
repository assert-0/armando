package agents;

import java.util.Date;
import java.util.Arrays;

import lombok.*;

import com.mindsmiths.dbAdapter.DBAdapterAPI;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.KeyboardOption;


@Getter
@Setter
public class Armando extends Agent {
    private Date lastInteractionTime;
    private String customerAnswer;
    private String userId = "2"; //hardkodirano za testiranje

    public Armando() {
        lastInteractionTime = new Date();
    }

    public Armando(String connectionName, String connectionId) {
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

    public void updateUserAnswer(User user, String answer) {
        user.setInterested(answer.equals("YES") ? true : false);
        DBAdapterAPI.updateUser(user);
        Log.info(user); // za testiranje
    }
} 