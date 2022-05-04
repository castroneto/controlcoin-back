package coltrolcoin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import coltrolcoin.models.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer>{ 
	
	@Query(value = "SELECT * FROM transactions WHERE id = ?1 and user_id = ?2", nativeQuery = true)
	Optional<Transactions> findTransactionByUserId(Number id, Long userId);

	
	@Query(value = "SELECT SUM(CASE WHEN entry = ?1 THEN value  END) FROM transactions WHERE user_id = ?2", nativeQuery = true)
	Optional<Float> findAndSumTransactionByUserId(boolean entry,  Long userId);


}
