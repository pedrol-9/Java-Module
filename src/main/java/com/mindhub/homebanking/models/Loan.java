package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String loanName;

    private double maxAmount;

    @ElementCollection
    @Column(name="Payments")
    private List<Integer> payments = new ArrayList<>();

    //builders
    public Loan(String loanName, double maxAmount, List<Integer> payments) {
        this.loanName = loanName;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Loan() {
    }

    // Accessory Methods
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    // toString Method
    @Override
    public String toString() {
        return "Loans{" +
                "id=" + id +
                ", loanName='" + loanName + '\'' +
                ", maxAmount=" + maxAmount +
                ", payments=" + payments +
                '}';
    }


}
