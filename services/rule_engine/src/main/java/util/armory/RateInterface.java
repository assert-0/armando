package util.armory;

import com.mindsmiths.armory.components.*;
import com.mindsmiths.armory.templates.BaseTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateInterface extends BaseTemplate {
    protected Title title;
    protected List<SubmitButton> submit;
}