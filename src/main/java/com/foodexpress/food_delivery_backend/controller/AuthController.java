package com.foodexpress.food_delivery_backend.controller;

import com.foodexpress.food_delivery_backend.dto.AuthDto;
import com.foodexpress.food_delivery_backend.dto.SigninReqDto;
import com.foodexpress.food_delivery_backend.model.Cart;
import com.foodexpress.food_delivery_backend.model.USER_ROLE;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.repository.CartRepository;
import com.foodexpress.food_delivery_backend.repository.UserRepository;
import com.foodexpress.food_delivery_backend.security.JwtProvider;
import com.foodexpress.food_delivery_backend.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartRepository cartRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.cartRepository = cartRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthDto> createUser(@RequestBody User user) throws Exception{
        User emailExists = userRepository.findByEmail(user.getEmail());
        if (emailExists != null){
            throw new Exception("Email is already used by another account");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        String authorities = user.getRole().toString();
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, auth);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthDto authDto = new AuthDto();
        authDto.setJwt(jwt);
        authDto.setMessage("Successfully Resgitrated");
        authDto.setRole(user.getRole());
        return new ResponseEntity<>(authDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthDto> signin(@RequestBody SigninReqDto req){
        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(email, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthDto authDto = new AuthDto();
        authDto.setJwt(jwt);
        authDto.setMessage("Successfully signed in");
        authDto.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (userDetails == null){
           throw new BadCredentialsException("Invalid user email");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
