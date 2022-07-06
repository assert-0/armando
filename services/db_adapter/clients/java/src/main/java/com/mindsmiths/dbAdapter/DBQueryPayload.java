package com.mindsmiths.db_adapter;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBQueryPayload implements Serializable {
    private String phone;
}
