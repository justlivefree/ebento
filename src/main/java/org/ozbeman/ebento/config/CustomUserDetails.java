package org.ozbeman.ebento.config;

import lombok.Getter;
import org.ozbeman.ebento.entity.Channel;
import org.ozbeman.ebento.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    private final User userModel;

    public CustomUserDetails(User userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userModel.getRoles().stream().map(String::valueOf).map(SimpleGrantedAuthority::new).toList();
    }

    public Channel getUserChannel() {
        return userModel.getChannel();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userModel.getPhoneNumber();
    }
}
