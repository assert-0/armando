package com.mindsmiths.armory.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseComponent {
    protected String src;
}
