package com.hamdan.concert.repository;

import com.hamdan.concert.entity.Concert;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    @Query(value = "select c.id, c.code, c.created_at, c.deleted_at, c.name, c.price, c.quota, c.time_from, c.time_to, c.updated_at, c.version " +
            "from Concert c " +
            "where " +
            "?1 >= c.time_from and c.time_to >= ?1 and c.quota > 0",
            nativeQuery = true)
    List<Concert> findByAvailability(Time currentTime);

    @Lock(LockModeType.OPTIMISTIC)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    @Query(value = "select c " +
            "from Concert c " +
            "where " +
            "c.code = ?1 and ?2 >= c.timeFrom and c.timeTo >= ?2 and c.quota > 0")
    Optional<Concert> findByCodeAndAvailability(String code, Time currentTime);

    @Lock(LockModeType.OPTIMISTIC)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    @Query(value = "select c " +
            "from Concert c " +
            "where " +
            "c.code = ?1 and ?2 >= c.timeFrom and c.timeTo >= ?2 and c.quota >= ?3")
    Optional<Concert> findByCodeAndAvailability(String code, Time currentTime, Integer qty);

    Optional<Concert> findByCode(String code);
}
