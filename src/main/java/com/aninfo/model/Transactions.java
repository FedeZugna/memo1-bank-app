package com.aninfo.model;

import javax.persistence.*;


@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long transactionCBU;

    private Double amount;
    private String transactionType;

    public Long getCbuTransacition(){ return transactionCBU;}
    public Long getId(){ return id;}
    public Double getAmount(){return amount;}
    public String getTransactionType(){return transactionType;}

    public void setCbuTransacition(long cbuTransacition){
        this.transactionCBU=cbuTransacition;
    }
    public void setId(Long id){
        this.id=id;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
    public void setTransactionType(String type){
        this.transactionType=type;
    }

}
