package com.ericsson.quotationmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Data
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(unique=true)
    String stockId;
    @ElementCollection()
    @MapKeyColumn (name="name")
    @Column(name="value_of_map")
    @CollectionTable(name="quotes_map", joinColumns=@JoinColumn(name="id"))
    Map<String, String> quotes = new HashMap<String, String>();
}
