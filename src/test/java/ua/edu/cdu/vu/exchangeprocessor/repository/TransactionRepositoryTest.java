package ua.edu.cdu.vu.exchangeprocessor.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.edu.cdu.vu.exchangeprocessor.TestHelper;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;
import ua.edu.cdu.vu.exchangeprocessor.entity.TransactionEntity;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;
import ua.edu.cdu.vu.exchangeprocessor.entity.projection.TransactionAggregationView;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void initData() {
        UserEntity userEntity = TestHelper.buildUserEntity();
        Long userId = userRepository.save(userEntity).getId();
        TransactionEntity transactionEntity1 = TestHelper.buildTransactionEntity(userId, TransactionType.BUY, 5);
        TransactionEntity transactionEntity2 = TestHelper.buildTransactionEntity(userId, TransactionType.SELL, 7);

        transactionRepository.saveAll(List.of(transactionEntity1, transactionEntity2));
    }

    @Test
    void shouldReturnTransactionSummary() {
        List<TransactionAggregationView> transactionSummary =
                transactionRepository.getRecentTransactionSummary(LocalDate.of(2021, 12, 19).atStartOfDay());
        assertThat(transactionSummary)
                .isNotNull()
                .hasSize(2)
                .extracting(TransactionAggregationView::getTransactionType)
                .containsExactly(TransactionType.SELL, TransactionType.BUY);
        assertThat(transactionSummary)
                .extracting(TransactionAggregationView::getTransactionCount)
                .containsExactly(7, 5);
    }
}
