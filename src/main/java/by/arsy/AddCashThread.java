package by.arsy;

import by.arsy.client.Client;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import by.arsy.service.ClientService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AddCashThread extends Thread {

    private ClientService service;
    private final Client client;
    private double clientBalance;
    private final double maxBalance;

    public AddCashThread(Client client) {
        this.client = client;
        this.clientBalance = client.getBalance();
        maxBalance = getMaxBalance(clientBalance);
    }

    @Override
    public void run() {
        while (maxBalance > grewBalance(clientBalance)) {
            try {
                clientBalance = grewBalance(clientBalance);
                service.addGrowBalanceToClient(client);
                Thread.sleep(60000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double getMaxBalance(double deposit) {
        return deposit / 100 * 207;
    }

    private double grewBalance(double deposit) {
        return deposit + deposit / 20;
    }
}
