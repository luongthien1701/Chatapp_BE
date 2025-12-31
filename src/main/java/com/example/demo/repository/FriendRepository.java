package com.example.demo.repository;

import com.example.demo.dto.FriendDTO;
import com.example.demo.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend,Long> {
    @Query("""
    select new com.example.demo.dto.FriendDTO(
        f.id,
        f.friend.id,
        f.friend.displayName,
        f.friend.avatarUrl,
        f.friend.status,
        f.friend.lastLogin
    )
    from Friend f
    where f.user.id = :userId and f.status = 'ACCEPTED'
""")
    List<FriendDTO> findAllByUserSent(@Param("userId") Long userId);

    @Query("""
    select new com.example.demo.dto.FriendDTO(
        f.id,
        f.user.id,
        f.user.displayName,
        f.user.avatarUrl,
        f.user.status,
        f.user.lastLogin
    )
    from Friend f
    where f.friend.id = :userId and f.status = 'ACCEPTED'
""")
    List<FriendDTO> findAllByUserReceived(@Param("userId") Long userId);


    @Query("""
    select f from Friend f
    where (f.user.id = :userId and f.friend.id = :friendId)
       or (f.user.id = :friendId and f.friend.id = :userId)
""")
    Friend findRelation(@Param("userId") Long userId, @Param("friendId") Long friendId);

    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
    @Query("""
    select f.friend.id
    from Friend f
    where f.user.id = :userId and f.status = 'ACCEPTED'
    """)
    List<Long> findFriendsWhereUserIsSender(@Param("userId") Long userId);

    @Query("""
    select f.user.id
    from Friend f
    where f.friend.id = :userId and f.status = 'ACCEPTED'
    """)
    List<Long> findFriendsWhereUserIsReceiver(@Param("userId") Long userId);

}
