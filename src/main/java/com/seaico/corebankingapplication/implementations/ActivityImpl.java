package com.seaico.corebankingapplication.implementations;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.services.ActivityI;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ActivityImpl implements ActivityI {

    @Override
    public Optional<Activity> createActivity(Activity activity) {

        return Optional.empty();
    }

    @Override
    public Optional<Activity> fetchActivity(Activity activity) {
        return Optional.empty();
    }

    @Override
    public Optional<Activity> fetActivityByUser(Activity activity, int perPage, int page) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Long> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Long> findById(Activity activity) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Activity activity) {
        return false;
    }

    @Override
    public List<Long> findAll() {
        return null;
    }

    @Override
    public List<Long> findAllById(Iterable<Activity> activities) {
        return null;
    }

    @Override
    public List<Long> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Long> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Long> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Long> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Long, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
