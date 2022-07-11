package com.mindsmiths.dbAdapter;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBFetchPayload implements Serializable {
    private int requestId;
    private String userId;
}
