package ua.edu.cdu.vu.exchangeprocessor.dto.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
    private String error;
    private String message;
}
