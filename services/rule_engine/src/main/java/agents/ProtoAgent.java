package agents;

import java.util.Arrays;

import lombok.*;

import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;
import com.mindsmiths.telegramAdapter.KeyboardData;
import com.mindsmiths.telegramAdapter.KeyboardOption;

import agents.Armando;
import agents.AgentHitl;
import agents.AgentAgent;
import agents.ActivityAgent;


@Getter
@Setter
@NoArgsConstructor
public class ProtoAgent extends AbstractAgent {

    public ProtoAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId);
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
        //Agents.createAgent(new Armando("telegram", getConnection("telegram"), String.valueOf((int) (Math.random() * 15))));
    }

    public void armandoCreator(String userId) {
        Agents.createAgent(new Armando("telegram", getConnection("telegram"), userId));
        if (!Agents.exists(ActivityAgent.ID)) {
            Agents.createAgent(new ActivityAgent("telegram", getConnection("telegram")));
        }
        if (!Agents.exists(ModelAgent.ID))
            Agents.createAgent(new ModelAgent());
    }

    public void handleFirstMessage(String text) {
        if (text.startsWith("/start ")) {
            armandoCreator(text.split(" ")[1]);
            Agents.deleteAgent(this);
        }
        else {
            sendRoleAssignment();
        }
    }

    public void handleAgentAssignment(String answer) {
        switch(answer) {
            case "USER":
                armandoCreator("2");
                Log.info("Created Armando and AA");
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
