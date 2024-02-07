package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByUserId(String userId);
}
