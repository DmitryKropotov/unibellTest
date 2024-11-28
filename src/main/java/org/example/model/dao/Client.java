package org.example.model.dao;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClientContact> clientContacts;

    public Client(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ClientContact> getClientContacts() {
        return clientContacts;
    }

    public void setClientContacts(List<ClientContact> clientContacts) {
        this.clientContacts = clientContacts;
    }
}
