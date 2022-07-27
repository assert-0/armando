
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Signals;
import com.mindsmiths.armory.events.SubmitEvent; 
import com.mindsmiths.armory.events.UserConnectedEvent;
import com.mindsmiths.armory.events.UserDisconnectedEvent;

import agents.Smith;
import agents.ModelAgent;


public class Runner extends RuleEngineService {
    @Override
    public void initialize() {
        configureSignals(getClass().getResourceAsStream("config/signals.yaml"));

        if (!Agents.exists(Smith.ID))
            Agents.createAgent(new Smith());

        //if (!Agents.exists(ModelAgent.ID))
        //    Agents.createAgent(new ModelAgent());

        configureSignals(
            Signals.on(UserConnectedEvent.class).sendTo(
                (e) -> Agents.getByConnection("armory", e.getConnectionId())
            ),
            Signals.on(SubmitEvent.class).sendTo(
                (e) -> Agents.getByConnection("armory", e.getConnectionId())
            ),
            Signals.on(UserDisconnectedEvent.class).sendTo(
                (e) -> Agents.getByConnection("armory", e.getConnectionId())
            )
        );
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
