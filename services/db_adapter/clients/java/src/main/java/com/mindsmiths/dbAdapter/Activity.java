package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

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
    public static Map<Integer, String> typeMap = new HashMap();
    static {
        typeMap.put(Activity.Type.ARMORY_LINK, "Armory link");
        typeMap.put(Activity.Type.PURCHASE_SIGNAL, "Zainteresirani za kupnju");
        typeMap.put(Activity.Type.SELLING_SIGNAL, "Zainteresirani za prodaju");
        typeMap.put(Activity.Type.APPRAISAL_SIGNAL, "Zainteresirani za procjenu");
    }
    private int type;
    private Date datetime;
}
