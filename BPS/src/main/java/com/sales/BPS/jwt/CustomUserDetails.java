package com.sales.BPS.jwt;

import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.AuthorityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Employee employee;
    private final AuthorityService authorityService;

    public CustomUserDetails(Employee employee, AuthorityService authorityService, AuthorityService authorityService1){
        this.employee = employee;
        this.authorityService = authorityService;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> authorityService.findAuthNameByEmpCode(employee.getEmpCode()));
        return collection;
    }

    @Override
    public String getPassword() {
        return employee.getEmpPw();
    }

    @Override
    public String getUsername() {
        return employee.getEmpName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
