package agents;

import java.util.Date;
import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;
import com.mindsmiths.ruleEngine.util.Log;

import agents.Armando;
import agents.AgentHitl;
import agents.AgentAgent;


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

    public void handleFirstMessage(String text) {
        if (text.startsWith("/start ")) {
            Agents.createAgent(new Armando("telegram", getConnection("telegram"), text.split(" ")[1]));
            Log.info("Armando " + text.split(" ")[1]);
            Agents.deleteAgent(this);
        }
        else {
            sendRoleAssignment();
            Log.info("Else");
        }
    }

    public void handleAgentAssignment(String answer) {
        switch(answer) {
            case "USER":
                Agents.createAgent(new Armando("telegram", getConnection("telegram"), "2")); // TODO:
                break;
            case "AGENT":
                Agents.createAgent(new AgentAgent("telegram", getConnection("telegram")));
                break;
            case "HITL":
                Agents.createAgent(new AgentHitl("telegram", getConnection("telegram")));
                break;
            default:
                // throw new Exception(); // TODO:
                break;
        }
        Agents.deleteAgent(this);
    }
}
