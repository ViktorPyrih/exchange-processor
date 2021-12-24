package ua.edu.cdu.vu.exchangeprocessor.entity.projection;

import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;

public interface TransactionAggregationView {

    TransactionType getTransactionType();

    Integer getTransactionCount();
}
