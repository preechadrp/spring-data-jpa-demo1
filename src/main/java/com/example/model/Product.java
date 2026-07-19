package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 1. กำหนดให้เป็น Auto Increment
	@Column(comment = "รหัสสินค้า") // 2. ใช้ attribute comment ของมาตรฐาน JPA แทน
	Long id;

	// 2. นำ comment เข้ามารวมใน @Column ของ JPA ได้เลย
	@Column(length = 100, nullable = false, comment = "ชื่อสินค้า") // 3. กำหนดความยาวของ String เป็น 100 และไม่อนุญาตให้เป็น null
	String name;

	@Column(precision = 18, scale = 2, comment = "ราคาสินค้า") // 4. กำหนดความยาวและทศนิยมเพื่อให้ตารางสร้างเป็น DECIMAL(18,2)
	BigDecimal price;

	//ปกติ jpa จะแปลงและจะหาชื่อ insert_date_time เพราะมันจะทำการแปลงชื่อฟิลด์จาก camel case เป็น snake_case โดยอัตโนมัติ
	//ถ้า config เพิ่มเติมใน application.properties 
	//ด้วย spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl  แต่จะมีผลทั้งระบบ
	//โดย
	//   ถ้าใส่ @Column(name = "insert_dateTime") ระบบจะใช้ชื่อคอลัมน์ insert_dateTime
	//   แต่ถ้าไม่ใส่ @Column(name = "insert_dateTime") ระบบจะใช้ชื่อคอลัมน์ insertDateTime
	@Column(name = "insert_dateTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
	LocalDateTime insertDateTime;
}