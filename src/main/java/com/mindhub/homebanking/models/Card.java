package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardHolder;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private CardColor cardColor;

    private String number;

    private int ccv;

    private LocalDate thruDate;

    private LocalDate fromDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Card(Client client, CardType cardType, CardColor cardColor, String number, int ccv, LocalDate thruDate, LocalDate fromDate) {
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.number = number;
        this.ccv = ccv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    public Card() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardType=" + cardType +
                ", cardColor=" + cardColor +
                ", number='" + number + '\'' +
                ", ccv=" + ccv +
                ", thruDate=" + thruDate +
                ", fromDate=" + fromDate +
                '}';
    }
}
