package com.rasha.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "`value`")
    private String value;

}