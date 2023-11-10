package com.smallworld.data;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    // Represent your transaction data here.

    private long mtn;
    private double amount;
    private String senderFullName;
    private int senderAge;
    private String beneficiaryFullName;
    private int beneficiaryAge;
    private int issueId;
    private boolean issueSolved;
    private String issueMessage;
}


