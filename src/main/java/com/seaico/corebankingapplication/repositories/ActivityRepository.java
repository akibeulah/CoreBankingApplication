package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findAllByUserId(User userId);
}
