package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Getter
    public enum NoInterestReason {
        NO_FUNDS("No funds"),
        NO_INTERESTING_OFFERS("No interesting offers"),
        GENERAL_NO_INTEREST("General no interest");

        public static NoInterestReason from(String text) {
            for (var reason : values()) {
                if (reason.text.equals(text)) {
                    return reason;
                }
            }
            return null;
        }

        private final String text;

        private NoInterestReason(String text) {
            this.text = text;
        }
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
    boolean boughtRE;
}
