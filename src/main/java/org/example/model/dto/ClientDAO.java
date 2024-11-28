package org.example.model.dto;

import java.util.List;

public class ClientDAO {
    private int id;

    private String name;

    private List<ClientContactDAO> clientContacts;

    public ClientDAO(int id, String name, List<ClientContactDAO> clientContacts) {
        this.id = id;
        this.name = name;
        this.clientContacts = clientContacts;
    }
}
