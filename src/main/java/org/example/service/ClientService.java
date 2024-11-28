package org.example.service;

import org.example.enums.ContactType;
import org.example.model.dao.Client;
import org.example.model.dao.ClientContact;
import org.example.model.dto.ClientContactDAO;
import org.example.model.dto.ClientDAO;
import org.example.repository.ClientContactRepository;
import org.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientContactRepository clientContactRepository;

    public ClientDAO addClient(MultipartHttpServletRequest httpServletRequest) {
        Map<String, String[]> parameters = httpServletRequest.getParameterMap();
        Client client = new Client(parameters.get("name")[0]);
        Client savedClient = clientRepository.save(client);
        return convertClientToClientDAO(savedClient);
    }

    public boolean addClientContact(int clientId, MultipartHttpServletRequest httpServletRequest) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if(clientOpt.isEmpty()) {
            return false;
        }
        Client client = clientOpt.get();
        String contact = httpServletRequest.getParameter("contact");
        List<ClientContact> clientContacts = new ArrayList<>();
        if(contact.matches("^\\+7\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{2}[\\s.-]?\\d{2}$")) {
            clientContacts.add(new ClientContact(contact, ContactType.PHONE_NUMBER.toString()));
        } else if (contact.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            clientContacts.add(new ClientContact(contact, ContactType.EMAIL.toString()));
        } else {
            throw new RuntimeException("Contact format exception");
        }
        client.setClientContacts(clientContacts);
        clientRepository.save(client);
        return true;
    }

    public List<ClientDAO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDAO> result = new ArrayList<>();
        clients.forEach(client -> {
            result.add(convertClientToClientDAO(client));
        });
        return result;
    }

    public ClientDAO getClientInfo(int id) {
          Optional<Client> client = clientRepository.findById(id);
          if(client.isPresent()) {
              return convertClientToClientDAO(client.get());
          } else {
              throw new RuntimeException("Client with id " + id + " doesn't exist");
          }
    }

    public List<ClientContactDAO> getAllClientContacts(int id) {
        List<ClientContact> clientContacts = clientContactRepository.findByClientId(id);
        return convertListClientContactToListClientContactDAO(clientContacts);
    }

    public List<ClientContactDAO> getClientContactsByType(int id, String clientType) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            return convertListClientContactToListClientContactDAO(client.get().getClientContacts().stream().
                    filter(clientContact -> clientContact.getType().equals(clientType)).toList());

        } else {
            throw new RuntimeException("Client with id " + id + " doesn't exist");
        }

    }

    private ClientDAO convertClientToClientDAO(Client client) {
        List<ClientContactDAO> contactDAOs = client.getClientContacts().stream().
                map(this::convertClientContactToClientContactDAO).collect(Collectors.toList());
        return new ClientDAO(client.getId(), client.getName(), contactDAOs);
    }

    private ClientContactDAO convertClientContactToClientContactDAO(ClientContact contact) {
        return new ClientContactDAO(contact.getId(), contact.getContact(), contact.getType(), contact.getClient().getId());
    }

    private List<ClientContactDAO> convertListClientContactToListClientContactDAO(List<ClientContact> clientContacts) {
        List<ClientContactDAO> result = new ArrayList<>();
        clientContacts.forEach(clientContact -> {
            result.add(convertClientContactToClientContactDAO(clientContact));
        });
        return result;
    }

}
