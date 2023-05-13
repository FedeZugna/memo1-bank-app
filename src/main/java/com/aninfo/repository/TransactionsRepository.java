package com.aninfo.repository;

import com.aninfo.model.Account;
import com.aninfo.model.Transactions;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface TransactionsRepository extends CrudRepository<Transactions,Long>{
    Account findAccountById(Long id);

    @Override
    List<Transactions> findAll();
}
