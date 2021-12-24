package ua.edu.cdu.vu.exchangeprocessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.edu.cdu.vu.exchangeprocessor.entity.TransactionEntity;
import ua.edu.cdu.vu.exchangeprocessor.entity.projection.TransactionAggregationView;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT transaction.type AS transactionType, sum(transaction.quantity) AS transactionCount FROM TransactionEntity transaction " +
            "WHERE transaction.createdAt >= (:dateTime) GROUP BY transaction.type")
    List<TransactionAggregationView> getRecentTransactionSummary(@Param("dateTime") LocalDateTime dateTime);
}
