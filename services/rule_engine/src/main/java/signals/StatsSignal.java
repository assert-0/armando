package signals; 

import java.util.List;

import com.mindsmiths.sdk.core.api.Signal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsSignal extends Signal {
    private String feature;
    private boolean growing;
    private double difference;
    private List<Double> statList;
}
