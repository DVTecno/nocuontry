package com.virtualpsychcare.service.implementation;

import com.virtualpsychcare.dto.AuthRegisterUserRequest;
import com.virtualpsychcare.dto.AuthLoginRequest;
import com.virtualpsychcare.dto.AuthResponse;
import com.virtualpsychcare.entities.RoleEntity;
import com.virtualpsychcare.entities.UserEntity;
import com.virtualpsychcare.repository.RoleEntityRepository;
import com.virtualpsychcare.repository.UserEntityRepository;
import com.virtualpsychcare.service.interfaces.IUserService;
import com.virtualpsychcare.utility.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService, IUserService {
    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(UserEntityRepository userEntityRepository, RoleEntityRepository roleEntityRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.roleEntityRepository = roleEntityRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por nombre de usuario
        UserEntity userEntity = userEntityRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Lista para almacenar las autoridades del usuario
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Añadir roles del usuario a las autoridades
        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Añadir permisos de los roles del usuario a las autoridades
        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // Crear y devolver UserDetails con la información del usuario y sus autoridades
        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnable(), userEntity.isAcconutNonExpired(), userEntity.isCredentialsNonExpired(), userEntity.isAccountNonLocked(), authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest userRequest) {
        String username = userRequest.username();
        String password = userRequest.password();

        Authentication authentication = authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(username, "Successful login", accessToken, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Transactional
    public AuthResponse registerUser(AuthRegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.username();
        String password = registerUserRequest.password();
        List<String> roles = registerUserRequest.roleRequest().roleListName();

        Set< RoleEntity> roleEntities = new HashSet<>(roleEntityRepository.findRoleEntitiesByRoleEnumIn(roles));
        if (roleEntities.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }

        UserEntity userEntity =  UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntities)
                .isEnable(true)
                .accountNonLocked(true)
                .acconutNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        UserEntity registeredUser = userEntityRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        registeredUser.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        registeredUser.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(registeredUser.getUsername(), registeredUser.getPassword(), authorityList);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(username, "User registered successfully", accessToken, true);
    }

}