package by.arsy.dao;

import by.arsy.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientDao {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String ADD_CLIENT = """
            INSERT INTO client (fio, date, balance)
            VALUES (:fio, :date, :balance)
            """;
    private static final String ADD_CONTACT = """
            INSERT INTO contact (client_id, phone, email)
            VALUES (:client_id, :phone, :email)
            """;

    private static final String GET_ID_TO_CLIENT = """
            SELECT id
            FROM client
            WHERE fio = :fio
            ORDER BY id DESC
            """;

    private static final String UPDATE_BALANCE_TO_ID = """
            UPDATE client
            SET balance = :balance
            WHERE id = :id
            """;

    private static final String FIND_CLIENT_WITH_PHONE = """
            SELECT client_id
            FROM contact
            WHERE phone = :phone
            """;

    private static final String FIND_CLIENT_WITH_EMAIL = """
            SELECT client_id
            FROM contact
            WHERE email = :email
            """;

    public void addClient(Client client) {
        namedJdbcTemplate.update(ADD_CLIENT, Map.of(
                "fio", client.getFio(),
                "date", client.getDate(),
                "balance", client.getBalance()));

        namedJdbcTemplate.update(ADD_CONTACT, Map.of(
                "client_id", client.getId(),
                "phone", client.getContact().getPhone(),
                "email", client.getContact().getEmail()));

    }

    public Long getId(Client client) {
        return namedJdbcTemplate.queryForObject(GET_ID_TO_CLIENT, Map.of(
                "fio", client.getFio()), Long.class);
    }

    public void updateBalance(Client client) {
        namedJdbcTemplate.update(UPDATE_BALANCE_TO_ID, Map.of(
                "balance", client.getBalance(),
                "id", client.getId()
        ));
    }

    public boolean checkClientWithPhone(Client client) {
        Long clientId = namedJdbcTemplate.queryForObject(FIND_CLIENT_WITH_PHONE, Map.of(
                "phone", client.getContact().getPhone()), Long.class);
        return clientId != null && client.getId() == clientId;
    }

    public boolean checkClientWithEmail(Client client) {
        Long clientId = namedJdbcTemplate.queryForObject(FIND_CLIENT_WITH_EMAIL, Map.of(
                "email", client.getContact().getEmail()), Long.class);
        return clientId != null && client.getId() == clientId;
    }
}
