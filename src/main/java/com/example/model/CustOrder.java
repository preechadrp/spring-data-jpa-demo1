package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "cust_order") // ระบุชื่อ Table ให้ตรงกับใน DB
public class CustOrder {

	@Id
	@Column(length = 50, nullable = false, comment = "เลข order") // กำหนดความยาวของ String เป็น 50 และไม่อนุญาตให้เป็น null
	private String orderId;

	@Column(length = 250, nullable = false, comment = "ชื่อลูกค้า") // กำหนดความยาวของ String เป็น 250 และไม่อนุญาตให้เป็น null
	private String customerName;

	@Column(precision = 18, scale = 2, comment = "ยอดรวมสินค้า")
	private BigDecimal totalAmount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
	private LocalDateTime insertDateTime;

}
