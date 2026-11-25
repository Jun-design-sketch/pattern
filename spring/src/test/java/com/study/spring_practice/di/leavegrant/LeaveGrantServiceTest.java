package com.study.spring_practice.di.leavegrant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LeaveGrantServiceTest {
    @Autowired
    private LeaveGrantService leaveGrantService;
    @Autowired
    private LeaveGrantRepository leaveGrantRepository;

    @Test
    void saveTestSuccess() {
        // given
        String leaveGrantId = "000";
        String userId = "user0";
        LocalDate validFrom = LocalDate.of(2026, 06, 06);
        int grantDays = 5;
        // when
        leaveGrantService.leaveGrantSave(leaveGrantId, userId, validFrom, grantDays);
        // then
        LeaveGrant leaveGrant = leaveGrantRepository.findByUserIdAndYear("user0", validFrom.getYear());
        assertEquals(leaveGrantId, leaveGrant.getLeaveGrantId());
        assertEquals(validFrom, leaveGrant.getValidFrom());
    }

    @Test
    void saveTestSuccessDuplicated() {
        // given
        String leaveGrantId = "001";
        String userId = "user1";
        LocalDate validFrom = LocalDate.of(2026, 06, 06);
        int grantDays = 5;
        // when
        leaveGrantService.leaveGrantSave(leaveGrantId, userId, validFrom, grantDays);
        assertThrows(
                RuntimeException.class,
                () -> leaveGrantService.leaveGrantSave(leaveGrantId, userId, validFrom, 999)
        );
        // then
        LeaveGrant leaveGrant = leaveGrantRepository.findByUserIdAndYear(userId, validFrom.getYear());
        assertEquals(leaveGrantId, leaveGrant.getLeaveGrantId());
        assertEquals(validFrom, leaveGrant.getValidFrom());
        assertEquals(grantDays, leaveGrant.getGrantDays());
    }

    @Test
    void saveTestFail() {
        // given
        String leaveGrantId = "002";
        String userId = "user2";
        LocalDate validFrom = LocalDate.of(2026, 06, 06);
        int grantDays = -2;
        // when
        assertThrows(
                IllegalArgumentException.class,
                () -> leaveGrantService.leaveGrantSave(leaveGrantId, userId, validFrom, grantDays)
        );
        // then
        LeaveGrant leaveGrant = leaveGrantRepository.findByUserIdAndYear(userId, validFrom.getYear());
        assertNull(leaveGrant);
    }
}
