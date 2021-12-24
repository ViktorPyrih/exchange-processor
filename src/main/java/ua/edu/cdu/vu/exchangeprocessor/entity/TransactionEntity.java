package ua.edu.cdu.vu.exchangeprocessor.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction", schema = "public")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private Integer price;
    private Integer quantity;
    private Long userId;

    @SuppressWarnings("deprecation")
    @Type(type = "ua.edu.cdu.vu.exchangeprocessor.entity.type.PostgreSQLEnum")
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
