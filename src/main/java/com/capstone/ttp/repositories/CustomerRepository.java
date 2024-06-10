package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends JpaRepository<User, Integer> {
}
