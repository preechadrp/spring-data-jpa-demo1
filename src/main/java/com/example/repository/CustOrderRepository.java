package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.CustOrder;

@Repository
public interface CustOrderRepository extends JpaRepository<CustOrder, String> {

	@Query(value = """
			SELECT * FROM cust_order WHERE order_id != :orderId and total_amount >= :total_amount
			""", nativeQuery = true)
	List<CustOrder> findTotalAmount(@Param("orderId") String orderId, @Param("total_amount") BigDecimal totalAmount);
}
