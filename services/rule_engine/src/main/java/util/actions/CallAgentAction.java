package util.actions;

import lombok.*;

import util.Action;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import agents.Armando;


@JsonDeserialize(as = CallAgentAction.class)
@Data
@NoArgsConstructor
public class CallAgentAction extends Action {
    public static final String ACTION_NAME = "CallAgent";

    private String agentId;

    public CallAgentAction(String agentId) {
        super(ACTION_NAME);
        this.agentId = agentId;
    }

    public void act(Armando agent) {
        agent.contactAgent(agentId);
    }
}
