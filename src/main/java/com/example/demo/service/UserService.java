package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserProfile;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private  final UsersRepository usersRepository;
    public UserService(UsersRepository usersrepository) {
        this.usersRepository = usersrepository;
    }
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
    public Users save(Users users) {
        return usersRepository.save(users);
    }
    public Users findByEmail(String email) {return usersRepository.findByEmail(email);}
    public Users findByDisplay_name(String display_name) {return usersRepository.findByDisplayName(display_name);}
    public List<Users> findAll() {
        return usersRepository.findAll();
    }
    public UserDTO findById(Long id) {
        return usersRepository.findUsersById(id);
    }
    public List<Users> findAllByLikeDisplayName(String likeDisplayName) {
        return usersRepository.findByDisplayNameContaining(likeDisplayName);
    }
    public UserProfile findProfileById(Long id) {
        return usersRepository.findUserProfilesById(id);
    }
    public String changePassword(Long user_id,String oldPassword, String newPassword) {
        Optional<Users> user=usersRepository.findById(user_id);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(oldPassword)) {
                user.get().setPassword(newPassword);
                usersRepository.save(user.get());
                return "Thay đôỉ mật khẩu thành công";
            }
            else return ("Mật khẩu không đúng vui lòng kiểm tra lại");
        }
        else return ("Lỗi,không thể thay đổi mật khẩu");
    }
}
