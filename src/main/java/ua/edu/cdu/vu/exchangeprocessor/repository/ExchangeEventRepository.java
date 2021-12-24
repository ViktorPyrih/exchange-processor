package ua.edu.cdu.vu.exchangeprocessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.edu.cdu.vu.exchangeprocessor.entity.ExchangeEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeEventRepository extends JpaRepository<ExchangeEvent, Long> {

    Optional<ExchangeEvent> findTopByOrderByIdDesc();

    List<ExchangeEvent> findAllByDatePublishedBetweenAndIdIn(LocalDateTime dateTimeLower, LocalDateTime dateTimeUpper, List<Long> ids);

    @Query("SELECT event.id FROM ExchangeEvent event WHERE event.datePublished BETWEEN (:dateTimeFrom) AND (:dateTimeTo)")
    List<Long> findAllIdsByDatePublishedBetween(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
