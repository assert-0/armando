package signals; 

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.mindsmiths.sdk.core.api.Signal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdSignal extends Signal {
    private String userId;
}
