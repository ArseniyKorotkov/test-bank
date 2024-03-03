package by.arsy.client;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Client {

    private  long id;
    private String fio;
    private LocalDateTime date;
    private Contact contact;
    private double balance;
}
