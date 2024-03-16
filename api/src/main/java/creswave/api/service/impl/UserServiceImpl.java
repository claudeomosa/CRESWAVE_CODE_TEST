package creswave.api.service.impl;

import creswave.api.dto.UserDTO;
import creswave.api.model.Role;
import creswave.api.model.User;
import creswave.api.repository.UserRepository;
import creswave.api.service.JwtService;
import creswave.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = new User();
        user.setFullname(userDto.getFullname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return toDto(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return toDto(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return toDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
       List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFullname(userDto.getFullname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        if (this.getCurrentUser().getId().equals(id) || this.getCurrentUser().getRole().equals(Role.ADMIN)) {
            return toDto(userRepository.save(user));
        }else {
            throw new RuntimeException("You can only update your own account");
        }
    }
    private User getCurrentUser() {
        UserDetails loggedInUserDetails =  jwtService.getLoggedInUser();
        Optional<User> user = userRepository.findByUsername(loggedInUserDetails.getUsername());

        return user.orElseThrow();
    }

    @Override
    public void deleteUser(Long id) {
        User currentUser = this.getCurrentUser();
        if (!currentUser.getId().equals(id) || !currentUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You can only delete your own account");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullname(user.getFullname());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
