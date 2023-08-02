package com.example.benyamine.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDto {
    private String accountId;
    private double balance;
    private String type;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private Long TotalElements;
    private List<AccountOperationsDto> accountOperationsDto;
}
