package ru.tinkoff.edu.java.scrapper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "link")
public class Link {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @Column(name = "last_check")
    private Timestamp lastCheck;

    @Column(name = "update_info")
    private String updateInfo;

    @ManyToMany(mappedBy = "links")
    private List<TgChat> tgChats = new ArrayList<>();
}
