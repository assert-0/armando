package com.mindsmiths.armory.templates;

import com.mindsmiths.armory.components.Description;
import com.mindsmiths.armory.components.Streak;
import com.mindsmiths.armory.components.SubmitButton;
import com.mindsmiths.armory.components.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreakInterface extends BaseTemplate {
    protected Title title;
    protected Description description;
    protected Streak streak;
    protected SubmitButton submitButton;
}
