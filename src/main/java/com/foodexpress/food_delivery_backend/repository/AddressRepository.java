package com.foodexpress.food_delivery_backend.repository;

import com.foodexpress.food_delivery_backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
