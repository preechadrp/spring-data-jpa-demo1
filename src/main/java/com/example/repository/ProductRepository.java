package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query; // <-- เปลี่ยนมาใช้ org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dto.ProductSummary;
import com.example.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// Spring Data จะเดา SQL ให้จากชื่อ Method (Query Method)
	List<Product> findByName(String name);

	// เขียน SQL เองแบบตรงไปตรงมา (Native SQL)
	@Query(value = "SELECT * FROM PRODUCT WHERE price > :minPrice", nativeQuery = true)
	List<Product> findExpensiveProductsByNativeSql(@Param("minPrice") BigDecimal minPrice);

	@Query("SELECT p FROM Product p WHERE p.price > :minPrice")
	List<Product> findExpensiveProducts(@Param("minPrice") BigDecimal minPrice);

	//update โดยใช้ Native SQL
	// ใส่ clearAutomatically = true เพื่อไม่ให้ค่าเก่าค้างในหน่วยความจำหลังจากอัปเดต
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE PRODUCT SET price = :newPrice WHERE id = :id", nativeQuery = true)
	int updatePriceByNativeSql(@Param("id") Long id, @Param("newPrice") BigDecimal newPrice);

	//update โดยใช้ JPQL
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Product p SET p.price = :newPrice WHERE p.id = :id")
	int updatePrice(@Param("id") Long id, @Param("newPrice") BigDecimal newPrice);

	// เปลี่ยนมาใช้ JPQL (Query ระดับ Object) เพื่อดึงข้อมูลใส่ Record/DTO โดยตรง
	// (สมมติว่า ProductSummary อยู่ใน package com.example.dto)
	// สังเกตว่าใน JPQL เราจะอ้างอิงชื่อคลาส "Product" แทนชื่อตาราง
	@Query("SELECT new com.example.dto.ProductSummary(p.id, p.name) FROM Product p")
	List<ProductSummary> findAllProductSummaries();
}
