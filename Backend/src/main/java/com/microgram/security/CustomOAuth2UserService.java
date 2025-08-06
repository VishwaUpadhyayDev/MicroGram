package com.microgram.security;

import com.microgram.model.AuthProvider;
import com.microgram.model.Users;
import com.microgram.repository.UserRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String email;
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            email = (String) attributes.get("email");
        } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            email = (String) attributes.get("email");
        } else {
            throw new OAuth2AuthenticationException("Login with " + registrationId + " is not supported");
        }
        
        if (!StringUtils.hasText(email)) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<Users> userOptional = userRepository.findByEmail(email);
        Users user;
        
        if (userOptional.isPresent()) {
            user = userOptional.get();
            
            // Update user details if needed
            if (!user.getProvider().toString().equalsIgnoreCase(registrationId)) {
                throw new OAuth2AuthenticationException(
                        "You're signed up with " + user.getProvider() + ". Please use that to login.");
            }
        } else {
            // Create new user
            user = registerNewUser(registrationId, attributes);
        }

        return new OAuth2UserPrincipal(user, attributes);
    }

    private Users registerNewUser(String registrationId, Map<String, Object> attributes) {
        Users user = new Users();
        
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            user.setEmail((String) attributes.get("email"));
            user.setUsername(generateUsername((String) attributes.get("email")));
            user.setProvider(AuthProvider.GOOGLE);
            user.setProviderId((String) attributes.get("sub"));
        } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            user.setEmail((String) attributes.get("email"));
            user.setUsername(generateUsername((String) attributes.get("email")));
            user.setProvider(AuthProvider.FACEBOOK);
            user.setProviderId((String) attributes.get("id"));
        }
        
        user.setRole("USER");
        user.setPassword(""); // No password for OAuth2 users
        
        return userRepository.save(user);
    }
    
    private String generateUsername(String email) {
        // Generate a username based on email
        String username = email.substring(0, email.indexOf('@'));
        
        // Check if username exists
        if (userRepository.existsByUsername(username)) {
            int i = 1;
            while (userRepository.existsByUsername(username + i)) {
                i++;
            }
            username = username + i;
        }
        
        return username;
    }
}