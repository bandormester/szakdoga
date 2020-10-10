package hu.szurdok.szakdogaservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiToken {
    Integer id;
    String token;
}
