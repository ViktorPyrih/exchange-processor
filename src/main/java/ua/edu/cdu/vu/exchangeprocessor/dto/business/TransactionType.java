package ua.edu.cdu.vu.exchangeprocessor.dto.business;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {
    BUY(1), SELL(-1);

    private final Integer correlation;

    public Integer correlation() {
        return correlation;
    }
}
