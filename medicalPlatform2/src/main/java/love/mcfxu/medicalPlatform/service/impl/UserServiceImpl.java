package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.domain.model.Role;
import love.mcfxu.medicalPlatform.domain.model.User;
import love.mcfxu.medicalPlatform.mapper.authority.RoleMapper;
import love.mcfxu.medicalPlatform.mapper.authority.UserMapper;
import love.mcfxu.medicalPlatform.mapper.authority.UserRoleMapper;
import love.mcfxu.medicalPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Transactional
    @Override
    public User findAllUserInfoByUsername(String username) {
        User user = userMapper.findByUsername(username);

        //用户的角色集合
        List<Role> roleList =  roleMapper.findRoleListByUserId((int) user.getId());

        user.setRoleList(roleList);

        return user;
    }


    @Override
    public User findSimpleUserInfoById(int userId) {
        return userMapper.findById(userId);
    }


    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public int deleteUserByName(String username) {
        return userMapper.deleteUserByName(username);
    }

    @Override
    public int findId(String username) {
        return userMapper.findId(username);
    }

    @Override
    public int deleteUserRoleById(int userId) {
        return userRoleMapper.deleteUserRoleById(userId);
    }


}
