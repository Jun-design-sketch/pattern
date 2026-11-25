package com.study.spring_practice.di.leavegrant;

import java.time.LocalDate;

public class LeaveGrant {
    private final String leaveGrantId;
    private final LocalDate validFrom;
    private final LocalDate validTo;
    private final int grantDays;

    public LeaveGrant(String leaveGrantId, LocalDate validFrom, LocalDate validTo, int grantDays) {
        this.leaveGrantId = leaveGrantId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.grantDays = grantDays;
    }

    public String getLeaveGrantId() {
        return leaveGrantId;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public int getGrantDays() {
        return grantDays;
    }
}
