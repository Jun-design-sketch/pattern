package com.study.spring_practice.di.leavegrant;

import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class LeaveGrantService {
    private final LeaveGrantRepository leaveGrantRepository;

    public LeaveGrantService(LeaveGrantRepository leaveGrantRepository) {
        this.leaveGrantRepository = leaveGrantRepository;
    }

    public void leaveGrantSave(String leaveGrantId, String userId, LocalDate validFrom, int grantDays) {
        if(grantDays <= 0){
            throw new IllegalArgumentException();
        }
        LocalDate validTo = validFrom.plusYears(2).minusDays(1);
        leaveGrantRepository.save(leaveGrantId, userId, validFrom, validTo, grantDays);
    }
}
