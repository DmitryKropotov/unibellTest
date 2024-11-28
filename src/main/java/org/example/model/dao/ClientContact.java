package org.example.model.dao;

import javax.persistence.*;

@Entity
@Table(name = "clientContact")
public class ClientContact {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "contact")
    private String contact;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public ClientContact(String contact, String type) {
        this.contact = contact;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public Client getClient() {
        return client;
    }
}
