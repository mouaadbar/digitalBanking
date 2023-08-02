package com.example.benyamine.repository;

import com.example.benyamine.entities.AccountOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountOperatinsRepository extends JpaRepository<AccountOperations,Long> {
      List<AccountOperations> findByBankAccountId(@Param("accountId")String accountId);
      Page<AccountOperations> findByBankAccountIdOrderByOperationDateDesc(Pageable pageable, @Param("accountId")String accountId);
}
