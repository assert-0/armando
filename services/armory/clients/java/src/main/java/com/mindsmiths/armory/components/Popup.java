package com.mindsmiths.armory.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mindsmiths.armory.components.Description;
import com.mindsmiths.armory.components.Title;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Popup extends BaseComponent {
    private Title title;
    private Description description;
    private String triggerText;
}
