package com.revature.repos;


import com.revature.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Set<Event> getEventByUserId(int userId);
}
