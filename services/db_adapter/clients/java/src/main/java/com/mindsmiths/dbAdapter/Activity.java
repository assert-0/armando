package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Serializable {
    public abstract class Type {
        public static int ARMORY_LINK = 0;
        public static int PURCHASE_SIGNAL = 1;
        public static int SELLING_SIGNAL = 2;
        public static int APPRAISAL_SIGNAL = 3;
    }

    private int type;
    private Date datetime;
}
