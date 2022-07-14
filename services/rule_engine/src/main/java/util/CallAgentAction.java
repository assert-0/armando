package util;

import lombok.*;

import agents.Armando;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallAgentAction implements Action {
    private String agentId;

    public void act(Armando agent) {
        agent.contactAgent(agentId);
    }
}
