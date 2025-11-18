package com.example.demo.repository;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserProfile;
import com.example.demo.model.ChatRoom;
import com.example.demo.model.Users;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findByDisplayName(String DisplayName);
    @Query(value ="""
            select u.id,u.display_name
            from Users as u
            where u.id=:userId""",nativeQuery = true)
    UserDTO findUsersById(@Param("userId") Long id);
    @Query(value ="""
            select u.id,u.display_name,u.email,u.password,u.phone
            from Users as u
            where u.id=:userId""",nativeQuery = true)
    UserProfile findUserProfilesById(@Param("userId") Long id);
    List<Users> findByDisplayNameContaining(String displayName);
    @Query(value ="""
            select u.*
            from Users as u
            where u.id=:userId""",nativeQuery = true)
    Users findUserById(@Param("userId") Long id);
    String findDisplayNameById(@Param("userId") Long id);
}
