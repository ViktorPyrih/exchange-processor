package ua.edu.cdu.vu.exchangeprocessor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;
import ua.edu.cdu.vu.exchangeprocessor.entity.TransactionEntity;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;
import ua.edu.cdu.vu.exchangeprocessor.exception.TransactionFailedException;
import ua.edu.cdu.vu.exchangeprocessor.repository.TransactionRepository;
import ua.edu.cdu.vu.exchangeprocessor.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final String AUTHENTICATION_PROBLEM = "Authentication problem occurred. ReAuthentication required";
    private static final String BUY_TRANSACTION_FAILED_TEMPLATE = "Not enough money to finish the operation. Balance: %s. Total price: %s";
    private static final String SELL_TRANSACTION_FAILED_TEMPLATE = "Not enough stock amount to finish the operation. Actual: %s. Required: %s";

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ExchangeEventService exchangeEventService;
    private final ProfileService profileService;

    @Transactional
    public void createTransaction(Integer stockSize, TransactionType transactionType) {
        Optional<UserEntity> userEntity = profileService.findPrincipalEntity();
        if (userEntity.isEmpty()) {
            throw new TransactionFailedException(AUTHENTICATION_PROBLEM);
        }
        initiateTransaction(userEntity.get(), stockSize, transactionType);
    }

    private void initiateTransaction(UserEntity userEntity, Integer stockSize, TransactionType transactionType) {
        Integer actualPrice = exchangeEventService.calculateActualPrice();

        if (transactionType == TransactionType.BUY) {
            initiateBuyTransaction(userEntity, stockSize, actualPrice);
        } else {
            initiateCellTransaction(userEntity, stockSize, actualPrice);
        }

        userRepository.save(userEntity);

        transactionRepository.save(TransactionEntity
                .builder()
                .type(transactionType)
                .userId(userEntity.getId())
                .price(actualPrice)
                .quantity(stockSize)
                .createdAt(LocalDateTime.now())
                .build());
    }

    private void initiateBuyTransaction(UserEntity userEntity, Integer stockSize, Integer actualPrice) {
        int totalPrice = actualPrice * stockSize;
        if (userEntity.getBalance() < totalPrice) {
            throw new TransactionFailedException(String.format(BUY_TRANSACTION_FAILED_TEMPLATE, userEntity.getBalance(), totalPrice));
        }

        userEntity.setStock(userEntity.getStock() + stockSize);
        userEntity.setBalance(userEntity.getBalance() - totalPrice);
    }

    private void initiateCellTransaction(UserEntity userEntity, Integer stockSize, Integer actualPrice) {
        int totalPrice = actualPrice * stockSize;
        if (userEntity.getStock() < stockSize) {
            throw new TransactionFailedException(String.format(SELL_TRANSACTION_FAILED_TEMPLATE, userEntity.getStock(), stockSize));
        }

        userEntity.setStock(userEntity.getStock() - stockSize);
        userEntity.setBalance(userEntity.getBalance() + totalPrice);
    }
}
