package by.arsy.control;

import by.arsy.AddCashThread;
import by.arsy.client.Client;
import by.arsy.client.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import by.arsy.service.ClientService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/startBank")
public class ClientController {

    @Autowired
    private ClientService service;

    @RequestMapping("/adding")
    public String addClient(@RequestParam String fio,
                            @RequestParam LocalDateTime date,
                            @RequestParam (value = "phone", required = false) String phone,
                            @RequestParam (value = "email", required = false) String email,
                            @RequestParam Long balance,
                            Model model) {

        if((phone != null || email != null) && balance >= 0) {
            Contact contact = Contact.builder()
                    .phone(phone)
                    .email(email)
                    .build();

            Client tryAddClient = Client.builder()
                    .fio(fio)
                    .date(date)
                    .contact(contact)
                    .balance(balance)
                    .build();

            if (service.isNewClient(tryAddClient)) {
                service.addClient(tryAddClient);
                model.addAttribute(tryAddClient);
                new AddCashThread(tryAddClient).start();
                return "clientPage";
            }
        }

        return "failPage";
    }

    @RequestMapping("/enter")
    public String enterClient(@RequestParam String fio,
                              @RequestParam String phoneOrEmail,
                              Model model) {

        if(phoneOrEmail != null) {
            Contact contact = Contact.builder()
                    .phone(phoneOrEmail)
                    .email(phoneOrEmail)
                    .build();

            Client tryEnterClient = Client.builder()
                    .fio(fio)
                    .contact(contact)
                    .build();

            if (!service.isNewClient(tryEnterClient)) {
                model.addAttribute(tryEnterClient);
                return "clientPage";
            }
        }

        return "failPage";
    }
}
