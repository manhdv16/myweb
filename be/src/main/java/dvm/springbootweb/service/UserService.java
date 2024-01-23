package dvm.springbootweb.service;

import dvm.springbootweb.entity.User;

public interface UserService {
    User findByUserName(String username);
    User saveOrUpdate(User user);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
}
