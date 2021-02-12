//package com.makeitlouder.shared.mappers;
//
//import com.makeitlouder.domain.security.Role;
//import com.makeitlouder.repositories.UserRepository;
//import org.mapstruct.Mapper;
//import org.mapstruct.Named;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.HashSet;
//
//
//public abstract class UserMapperDecorator implements UserMapper  {
//
//    @Autowired
//    @Qualifier("delegate")
//    private UserMapper userMapper;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Collection<Role> roleStringToRoleEntity(Collection<String> roles) {
//        Collection<Role> n = userMapper.roleStringToRoleEntity(roles);
////        Collection<Role> newRoles = new HashSet<>();
//        for (String role : roles) {
//            n.add(Role.builder().name(role).build());
//        }
//        return n;
//    }
//
//    @Override
//    public Collection<String> roleEntityToRoleString(Collection<Role> roles) {
//        Collection<String> n = userMapper.roleEntityToRoleString(roles);
////        Collection<String> newRoles = new HashSet<>();
//        for (Role role : roles) {
//            n.add(role.getName());
//        }
//        return n;
//    }
//
//}
