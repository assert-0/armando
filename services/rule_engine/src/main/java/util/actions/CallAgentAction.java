package util.actions;

import lombok.*;

import util.Action;

import agents.Armando;


@Data
@NoArgsConstructor
public class CallAgentAction extends Action {
    public static final String ACTION_NAME = "CallAgent";

    private String agentId;

    public CallAgentAction(String agentId) {
        super(ACTION_NAME);
        this.agentId = agentId;
    }

    public void act(Object value) {
        var agent = (Armando)value;
        agent.contactAgent(agentId);
    }
}
