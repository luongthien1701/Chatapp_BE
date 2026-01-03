package com.example.demo.service;

import com.example.demo.dto.SenderInfo;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserProfile;
import com.example.demo.manager.GlobalOnlineManager;
import com.example.demo.manager.LocateManager;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Users> getOnlineUsers() {
        List<Long> onlineUsers = GlobalOnlineManager.getOnlineUserIds();
        return usersRepository.findByIdAndProfileVisibilityNot(onlineUsers, Users.ProfileVisibility.PUBLIC);
    }
    public SenderInfo findById(Long id) {
        return usersRepository.findUsersById(id);
    }
    public List<Users> findAllByLikeDisplayName(String likeDisplayName) {
        return usersRepository.findByDisplayNameContaining(likeDisplayName);
    }
    public void changeStatus(Long id,double lat,double lon) {
        Users user=usersRepository.findUserById(id);
        if (user!=null){
            user.setProfileVisibility(user.getProfileVisibility()== Users.ProfileVisibility.PUBLIC? Users.ProfileVisibility.PRIVATE: Users.ProfileVisibility.PUBLIC);
            usersRepository.save(user);
            LocateManager.addLocate(id,lat,lon);
        }
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
    public void changeAvatar(Long user_id,String avatar) {
        Users user=usersRepository.findUserById(user_id);
        if (user==null) throw new NullPointerException("Không thể tìm thấy người dùng");
        user.setAvatarUrl(avatar);
        usersRepository.save(user);
    }
    public void changeProfile(UserProfile userProfile) {
        Users user=usersRepository.findUserById(userProfile.getId());
        if (user==null) throw new NullPointerException("Người dùng không tồn tại");
        user.setDisplayName(userProfile.getDisplayName());
        user.setEmail(userProfile.getEmail());
        user.setPhone(userProfile.getPhone());
        usersRepository.save(user);
    }
}
