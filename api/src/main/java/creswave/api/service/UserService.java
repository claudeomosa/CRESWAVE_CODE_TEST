package creswave.api.service;

import creswave.api.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDto);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDto);
    UserDTO getUserByUsername(String username);
    void deleteUser(Long id);
}
