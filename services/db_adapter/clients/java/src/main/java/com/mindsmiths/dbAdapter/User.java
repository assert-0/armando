package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    enum NoInterestReason {
        NO_FUNDS,
        NO_INTERESTING_OFFERS,
        GENERAL_NO_INTEREST
    }

    String id;
    String name;
    String surname;
    String gender;
    int age;
    String email;
    String phoneNumber;
    int amountOfBoughtRE;
    List<Date> datesOfPurchaseInAgency;
    Date lastBoughtRE;
    Date lastInteractionWithAgent;
    boolean interested;
    NoInterestReason noInterestReason;
}
