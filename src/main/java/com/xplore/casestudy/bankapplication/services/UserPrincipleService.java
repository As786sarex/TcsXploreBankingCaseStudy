package com.xplore.casestudy.bankapplication.services;

import com.xplore.casestudy.bankapplication.models.User;
import com.xplore.casestudy.bankapplication.models.UserPrinciple;
import com.xplore.casestudy.bankapplication.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User loginUser = userRepository.findByUsername(s).orElseThrow(() -> {
            throw new UsernameNotFoundException(s + " Not found");
        });
        System.out.println(loginUser);
        UserPrinciple principle = new UserPrinciple(loginUser);
        System.out.println(principle.getAuthorities());
        return principle;
    }
}
