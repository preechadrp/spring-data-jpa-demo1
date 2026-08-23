package com.example.model;

import com.example.model.compositekey.OrderItemId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ORDER_ITEM")
@IdClass(OrderItemId.class) // ประกาศว่าคลาสนี้ใช้ Composite Key จาก OrderItemId ,ใช้ @IdClass เพื่อระบุว่า Entity นี้มี Primary Key แบบ Composite Key โดยใช้คลาส OrderItemId เป็นตัวแทนของ Composite Key
public class OrderItem {

	@Id // ระบุ @Id ตัวที่ 1
	@Column(name = "order_id")
	private Long orderId;

	@Id // ระบุ @Id ตัวที่ 2
	@Column(name = "item_no")
	private Integer itemNo;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "quantity")
	private Integer quantity;
}