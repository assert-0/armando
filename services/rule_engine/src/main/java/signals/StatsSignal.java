package signals; 

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.mindsmiths.sdk.core.api.Signal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsSignal extends Signal {
    private String feature;
    private boolean growing;
    private double difference;
}
