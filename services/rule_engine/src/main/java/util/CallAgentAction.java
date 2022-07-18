package util;

import lombok.*;

import util.Action;
import agents.Armando;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallAgentAction extends Action {
    private String agentId;

    public void act(Armando agent) {
        agent.contactAgent(agentId);
    }
}
