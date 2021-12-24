package ua.edu.cdu.vu.exchangeprocessor.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByIdentifier(String identifier);

    List<UserEntity> findAllByDateOfLastVisitLessThanEqual(LocalDateTime dateTime);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity user SET user.balance = user.balance + (:delta) WHERE user.dateOfLastVisit >= (:dateTime)")
    void updateRecentActiveUsersBalance(@Param("delta") Integer delta, @Param("dateTime") LocalDateTime dateTime);
}
