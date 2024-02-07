package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityI extends JpaRepository<Long, Activity> {
    public Optional<Activity> createActivity(Activity activity);
    public Optional<Activity> fetchActivity(Activity activity);
    public Optional<Activity> fetActivityByUser(Activity activity, int perPage, int page);
}
