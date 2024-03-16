package creswave.api.service;
/*
 * Service for User
 * This interface is used to define the methods for User, such as createUser, getUserById, getAllUsers, updateUser, getUserByUsername, and deleteUser.
 * The methods are implemented in the UserServiceImpl class.
 */
import creswave.api.dto.UserDTO;
import creswave.api.model.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDto);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDto);
    UserDTO getUserByUsername(String username);
    UserDTO toDto(User user);
    void deleteUser(Long id);
}
