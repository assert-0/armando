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
    protected Title spTitle;
    protected Image spImage1;
    protected List<SubmitButton> spActions1;
    protected Image spImage2;
    protected List<SubmitButton> spActions2;
    protected Image spImage3;
    protected List<SubmitButton> spActions3;
    protected Title slTitle;
    protected Image slImage1;
    protected List<SubmitButton> slActions1;
    protected Image slImage2;
    protected List<SubmitButton> slActions2;
    protected Image slImage3;
    protected List<SubmitButton> slActions3;
}