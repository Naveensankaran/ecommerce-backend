package com.naveen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

	public List<Order> findByUserId(Long userId);

}
