package org.example.model.dto;

public class ClientContactDAO {
    private int id;

    private String contact;

    private String type;

    private int clientId;

    public ClientContactDAO(int id, String contact, String type, int clientId) {
        this.id = id;
        this.contact = contact;
        this.type = type;
        this.clientId = clientId;
    }
}
