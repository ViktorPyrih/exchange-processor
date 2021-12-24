package ua.edu.cdu.vu.exchangeprocessor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;
import ua.edu.cdu.vu.exchangeprocessor.entity.TransactionEntity;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestHelper {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2021, 12, 19, 17, 42);

    public static TransactionEntity buildTransactionEntity(Long userId, TransactionType type, int quantity) {
        return TransactionEntity
                .builder()
                .type(type)
                .userId(userId)
                .createdAt(DATE_TIME)
                .quantity(quantity)
                .price(5)
                .build();
    }

    public static UserEntity buildUserEntity() {
        return UserEntity
                .builder()
                .name("Viktor")
                .email("email@gmail.com")
                .balance(10)
                .stock(0)
                .identifier(UUID.randomUUID().toString())
                .dateOfLastVisit(LocalDateTime.now())
                .build();
    }
}
