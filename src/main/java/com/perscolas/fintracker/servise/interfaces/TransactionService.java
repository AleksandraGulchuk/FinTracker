package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;

/**
 * Service interface for managing transactions.
 * - Provides a method to get a summary of transactions for a user based on a specific time duration.
 */
public interface TransactionService {

    TransactionsSummaryDto getTransactionsSummary(String userName, String timeDuration);

}
