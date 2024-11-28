package org.example.controller;

import org.example.model.dto.ClientContactDAO;
import org.example.model.dto.ClientDAO;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/clientAPI")
public class ClientController {

    @Autowired
    protected ClientService clientService;

    @PostMapping("/addClient")
    public ClientDAO addClient(MultipartHttpServletRequest httpServletRequest) {
        return clientService.addClient(httpServletRequest);
    }

    @PostMapping("/addClientContact")
    public boolean addClientContact(int clientId, MultipartHttpServletRequest httpServletRequest) {
        return clientService.addClientContact(clientId, httpServletRequest);
    }

    @GetMapping("/getAllClients")
    public List<ClientDAO> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/getClientInfo")
    public ClientDAO getClientInfo(int id) {
        return clientService.getClientInfo(id);
    }

    @GetMapping("/getAllClientContacts")
    public List<ClientContactDAO> getAllClientContacts(int id) {
        return clientService.getAllClientContacts(id);
    }

    @GetMapping("/getClientContactsByType")
    public List<ClientContactDAO> getClientContactsByType(int id, String clientType) {
        return clientService.getClientContactsByType(id, clientType);
    }
}
