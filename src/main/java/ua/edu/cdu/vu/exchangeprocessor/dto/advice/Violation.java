package ua.edu.cdu.vu.exchangeprocessor.dto.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Violation {
    private String fieldName;
    private String message;
}
