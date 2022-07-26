package signals; 

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.mindsmiths.dbAdapter.User;
import com.mindsmiths.sdk.core.api.Signal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingSignal extends Signal {
    private User user;
    private String date;
}