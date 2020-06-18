package com.bytestree.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytestree.restful.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

}
