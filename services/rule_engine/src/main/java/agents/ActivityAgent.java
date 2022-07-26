package agents;

import lombok.*;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

import com.mindsmiths.dbAdapter.Activity;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;

import signals.ActivityUsersSignal;


@Getter
@Setter
@NoArgsConstructor
public class ActivityAgent extends AbstractAgent {
    private Map<String, List<User>> activitiesMap = new HashMap<>();
    private Date lastUpdate = new Date();
    
    public ActivityAgent(String connectionName, String connectionId) {
        super(connectionName, connectionId);
        activitiesMap.put(String.valueOf(Activity.Type.APPRAISAL_SIGNAL), new ArrayList<User>());
        activitiesMap.put(String.valueOf(Activity.Type.PURCHASE_SIGNAL), new ArrayList<User>());
        activitiesMap.put(String.valueOf(Activity.Type.SELLING_SIGNAL), new ArrayList<User>());
    }

    public void sendActivityUsers() {
        for (String key : activitiesMap.keySet()) 
            this.sendFirst(AgentAgent.class, new ActivityUsersSignal(key, new HashSet(activitiesMap.get(key).stream().map(value -> value.getName() + " " + value.getSurname()).toList())));
        for (String key : activitiesMap.keySet()) 
            Log.info(activitiesMap.get(key).stream().map(value -> value.getName() + " " + value.getSurname()).toList());
    }
}
