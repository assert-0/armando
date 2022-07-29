package signals; 

import java.util.List;
import java.util.HashSet;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.sdk.core.api.Signal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityUsersSignal extends Signal {
    private String signalName;
    private HashSet<String> users;
}
