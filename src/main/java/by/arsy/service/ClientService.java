package by.arsy.service;

import by.arsy.client.Client;
import by.arsy.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientDao clientDao;

    public void addClient(Client client) {
        clientDao.addClient(client);
        client.setId(clientDao.getId(client));
    }

    public void addGrowBalanceToClient(Client client) {
        clientDao.updateBalance(client);
    }

    public boolean isNewClient(Client client) {
        boolean haveClientToFioAndPhone = clientDao.checkClientWithPhone(client);
        boolean haveClientToFioAndEmail = clientDao.checkClientWithEmail(client);
        return haveClientToFioAndEmail || haveClientToFioAndPhone;
    }
}
