package com.mindsmiths.graphMaister;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGraphDatePayload implements Serializable {
    private int requestId;
    private String startDate;
    private String dateFmt;
    private List<Double> y;
    private String graphFmt;
    private String title;
    private String xlabel;
    private String ylabel;
    private String imageFormat;
}
