package agents;

import lombok.*;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.sdk.core.api.Signal;
import com.mindsmiths.telegramAdapter.TelegramAdapterAPI;


@Getter
@Setter
public abstract class AbstractAgent extends Agent {
    protected AbstractAgent() {
    }

    protected AbstractAgent(String agentId) {
        id = agentId;
    }

    protected AbstractAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId);
    }

    protected AbstractAgent(String connectionName, String connectionId, String agentId) {
        super(connectionName, connectionId);
        id = agentId;
    }

    public void sendMessage(String text) {
        String chatId = getConnections().get("telegram");
        TelegramAdapterAPI.sendMessage(chatId, text);
    }

    public <T extends Agent> void sendBroadcast(Class<T> agentCls, Signal signal, String entryPoint) {
        var agents = Agents.getByType(agentCls);
        for (var agent : agents) {
            send(agent.getId(), signal, entryPoint);
        }
    }

    public <T extends Agent> void sendBroadcast(Class<T> agentCls, Signal signal) {
        sendBroadcast(agentCls, signal, "signals");
    }

    public <T extends Agent> void sendFirst(Class<T> agentCls, Signal signal, String entryPoint) {
        var agents = Agents.getByType(agentCls);
        var iter = agents.iterator();
        if (iter.hasNext()) {
            send(iter.next().getId(), signal, entryPoint);
        }
    }

    public <T extends Agent> void sendFirst(Class<T> agentCls, Signal signal) {
        sendFirst(agentCls, signal, "signals");
    }
}
