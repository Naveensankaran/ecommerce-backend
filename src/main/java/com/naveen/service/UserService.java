package com.naveen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.naveen.entity.User;
import com.naveen.dto.UserDto;
import com.naveen.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());

        User saved = userRepo.save(user);
        return convertToDto(saved);
    }

    public UserDto getUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(userDto.getName());
        existing.setEmail(userDto.getEmail());
        existing.setPassword(userDto.getPassword());
        existing.setPhoneNumber(userDto.getPhoneNumber());
        existing.setAddress(userDto.getAddress());

        User updated = userRepo.save(existing);
        return convertToDto(updated);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        // ❌ intentionally skip password in DTO for response security
        return dto;
    }
}
