package creswave.api.service.impl;

import creswave.api.dto.UserDTO;
import creswave.api.model.User;
import creswave.api.repository.UserRepository;
import creswave.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
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
