package com.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.model.Product;
import com.example.repository.CustOrderRepository;
import com.example.repository.ProductRepository;
import com.fasterxml.uuid.Generators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringDataJpaDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaDemo1Application.class, args);
	}

	@Bean
	CommandLineRunner demoProduct(ProductRepository repository) {
		return (args) -> {
			// == Insert ข้อมูล ==
			// ตอน Insert ให้ส่ง ID เป็น null ไปก่อน เพื่อให้ DB รัน Auto-increment เอง
			Product p1 = new Product(null, "Gaming Mouse", new BigDecimal("1500.00"), LocalDateTime.now());
			Product p2 = new Product(null, "Mechanical Keyboard", new BigDecimal("3500.00"), LocalDateTime.now());

			p1 = repository.saveAndFlush(p1); // สังเกตว่า p1 ตัวใหม่จะได้ ID กลับมาจาก DB
			repository.saveAndFlush(p2);
			log.info("--- Inserted First Product: " + p1);

			log.info("--- findByName ---");
			repository.findByName("Gaming Mouse").forEach(product -> log.info("Found by name: " + product.toString()));

			// == Find ข้อมูลทั้งหมด ==
			log.info("--- All Products ---");
			repository.findAll().forEach(product -> log.info(product.toString()));

			// == ลองใช้ Custom Query แบบระบุราคา ==
			log.info("--- Products expensive than 2000 ---");
			repository.findExpensiveProducts(new BigDecimal("2000.00"))
					.forEach(product -> log.info(product.toString()));

			// == อัปเดตข้อมูลด้วย SQL ตรงๆ ==
			log.info("--- Updating Price ---");
			int effRows = repository.updatePrice(p1.getId(), new BigDecimal("1200.00"));
			log.info("Update success? : " + effRows + " row(s) affected.");

			//== ดูผลลัพธ์หลังอัปเดต
			log.info("--- Updated Product: " + repository.findById(p1.getId()).orElse(null));

			//== ดึงแค่บางฟิลด์เข้า record
			log.info("--- Select some field to record ---");
			var result = repository.findAllProductSummaries();
			result.forEach(productSummary -> log.info(productSummary.toString()));

		};
	}

	@Bean
	CommandLineRunner demoCustOrder(CustOrderRepository custOrderRepository) {
		return (args) -> {

			//===== ทดสอบการ Insert และ Update ข้อมูลใน CustOrder โดย primary key เรา generate เอง =====
			UUID newUuid = Generators.timeBasedEpochGenerator().generate();

			var custOrder = new com.example.model.CustOrder()
					.setOrderId("order:" + newUuid.toString().substring(0, 8)) // สร้าง orderId แบบง่ายๆ โดยเอาแค่ 8 ตัวแรกของ UUID
					.setCustomerName("John Doe")
					.setTotalAmount(new BigDecimal("5000.00"))
					.setInsertDateTime(LocalDateTime.now());

			var cust1 = custOrderRepository.save(custOrder);
			log.info("cust1 : " + cust1.toString());

			custOrderRepository.findTotalAmount(cust1.getOrderId(), new BigDecimal("5000.00")).forEach(order -> {

				log.info("Update order: " + order.getOrderId());

				order.setTotalAmount(new BigDecimal(4000.00));
				custOrderRepository.save(order);

			});

		};
	}
}
