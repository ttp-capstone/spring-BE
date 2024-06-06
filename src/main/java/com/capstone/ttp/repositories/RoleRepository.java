package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.Role;
import com.capstone.ttp.entitiy.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}