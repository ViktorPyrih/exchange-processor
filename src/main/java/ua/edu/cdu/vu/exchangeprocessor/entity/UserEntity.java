package ua.edu.cdu.vu.exchangeprocessor.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateOfLastVisit;
    private String email;
    private String name;
    private String identifier;
    private Integer balance;
    private Integer stock;
}
