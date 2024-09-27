package com.example.amanetpfe.dto;

import com.example.amanetpfe.Entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AccountRequest")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRequest;

    private String accountType;
    private String status;  // PENDING, APPROVED, REJECTED
    private Date requestDate;
    private Date responseDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
}
