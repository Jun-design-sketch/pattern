package com.study.spring_practice.di.leavegrant;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class LeaveGrantRepository {
    private final HashMap<GrantKey, LeaveGrant> storedData = new HashMap<GrantKey, LeaveGrant>(); // non-atomic
    private final Lock lock = new ReentrantLock();
    private record GrantKey(String userId, int grantYear){};

    public void save(String leaveGrantId, String userId, LocalDate validFrom, LocalDate validTo, int grantDays) {
        lock.lock();
        try {
            LeaveGrant existingGrant = storedData.putIfAbsent(new GrantKey(userId, validFrom.getYear()), new LeaveGrant(leaveGrantId, validFrom, validTo, grantDays));
            System.out.println("[DEBUG] LeaveGrantRepository.save(): " + existingGrant);
            if(existingGrant != null) {
                throw new RuntimeException();
            }
        } finally {
            lock.unlock();
        }

    }

    public LeaveGrant findByUserIdAndYear(String userId, int grantYear) {
        lock.lock();
        try {
            return storedData.get(new GrantKey(userId, grantYear));
        } finally {
            lock.unlock();
        }
    }

}
