package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;

public interface TransactionService {

    TransactionsSummaryDto getTransactionsSummary(String userName, String timeDuration);

}
