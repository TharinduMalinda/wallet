package com.wallet.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * dummy user service for JWT.
 * Should not used this in PROD
 * Should used proper user detail retrieval service.
 *
 * @author Malinda
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(
                "admin","admin",new ArrayList<>()
        );
    }
}
