package hu.szurdok.szakdogaservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterStatus {
    boolean isSuccessful;
    String message;
}
