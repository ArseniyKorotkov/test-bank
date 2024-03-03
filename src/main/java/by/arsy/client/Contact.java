package by.arsy.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private long personId;
    private String phone;
    private String email;
}
