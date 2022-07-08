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

import signals.UserIdSignal;


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

    public Armando(String connectionName, String connectionId, String userId) {
        super(connectionName, connectionId);
        lastInteractionTime = new Date();
        this.userId = userId;
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

    public void sendUserSignal() {
        UserIdSignal signal = new UserIdSignal();
        signal.setUserId(getUserId());
        if (customerAnswer.equals("YES")) send("AGENT", signal, "signals");
        else send("HITL", signal, "signals");
    }
} 