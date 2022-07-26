package agents;

import lombok.*;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.mindsmiths.dbAdapter.Activity;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.ruleEngine.model.Agent;


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
}
