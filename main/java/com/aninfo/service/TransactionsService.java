package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.repository.AccountRepository;
import com.aninfo.model.Transactions;
import com.aninfo.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transactions createTransaction(Transactions transaction){
        return transactionsRepository.save(transaction);
    }

    public Collection<Transactions> getTransactions(){
        return transactionsRepository.findAll();
    }

    public Optional<Transactions> findById(Long id){
        return transactionsRepository.findById(id);
    }

    public Collection<Transactions> getTransactionsByCbu(long cbu){
        Collection<Transactions> transactionsCBU = new ArrayList<>();
        Collection<Transactions> fullTransactions = transactionsRepository.findAll();
        for (Transactions transactions: fullTransactions){
            if(transactions.getCbuTransacition().equals(cbu)){
                transactionsCBU.add(transactions);
            }
        }

        return transactionsCBU;
    }

    public void deleteById(Long cbu) {
        transactionsRepository.deleteById(cbu);
    }




}
