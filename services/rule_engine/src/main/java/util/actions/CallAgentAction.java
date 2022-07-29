package util.actions;

import lombok.*;

import util.Action;

import com.mindsmiths.ruleEngine.model.Agent;

import agents.Armando;


@Data
@NoArgsConstructor
public class CallAgentAction extends Action {
    public static final String ACTION_NAME = "CallAgent";

    private String agentClasString;

    public CallAgentAction(Class<? extends Agent> agentClass) {
        super(ACTION_NAME);
        this.agentClasString = agentClass.getName();
    }

    public CallAgentAction(String agentClasString) {
        super(ACTION_NAME);
        this.agentClasString = agentClasString;
    }

    public void act(Object value) {
        var agent = (Armando)value;
		try {
			var agentClass = Class.forName(agentClasString);
            agent.contactAgent((Class<Agent>)agentClass);
		} catch (ClassNotFoundException | ClassCastException ignored) {
		}
    }
}
