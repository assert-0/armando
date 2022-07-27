package com.mindsmiths.graphMaister;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGraphPayload implements Serializable {
    private int requestId;
    private List<Double> points;
    private String title;
    private String xlabel;
    private String ylabel;
    private String imageFormat;
}
