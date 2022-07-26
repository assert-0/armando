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
public class DisplayInterface extends BaseTemplate {
    protected Title title;
    protected Image image;
    protected Description description;
    protected Title rateTitle;
    protected List<SubmitButton> rateBtn;
}