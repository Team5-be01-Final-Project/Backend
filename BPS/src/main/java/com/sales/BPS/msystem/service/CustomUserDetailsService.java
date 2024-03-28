package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.AuthorityRepository;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository, AuthorityRepository authorityRepository) {
        this.employeeRepository = employeeRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Integer empCode = Integer.parseInt(username);
            Employee employee = employeeRepository.findById(empCode)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Employee not found with emp_code: " + empCode));

            Authority authority = authorityRepository.findByEmpCode(empCode)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Authority not found for employee with emp_code: " + empCode));

            String authCode = authority.getAuthCode();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            switch (authCode) {
                case "AUTH000":
                    grantedAuthorities.add(new SimpleGrantedAuthority("RESIGNED"));
                    break;
                case "AUTH001":
                    grantedAuthorities.add(new SimpleGrantedAuthority("ITADMIN"));
                    break;
                case "AUTH002":
                    grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
                    break;
                case "AUTH003":
                    grantedAuthorities.add(new SimpleGrantedAuthority("MANAGER"));
                    break;
                case "AUTH004":
                    grantedAuthorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
                    break;
                default:
                    throw new UsernameNotFoundException("Invalid authority code: " + authCode);
            }

            return new User(employee.getEmpCode().toString(), employee.getEmpPw(), grantedAuthorities);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Employee code must be an integer");
        }
    }
}

