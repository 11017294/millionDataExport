package com.chen.milliondataexport.export.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.milliondataexport.export.entity.User;
import com.chen.milliondataexport.export.mapper.UserMapper;
import com.chen.milliondataexport.export.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.milliondataexport.export.util.MyExport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author MaybeBin
 * @since 2022-07-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void dataExport(HttpServletResponse response) throws IOException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        MyExport.dataExport(wrapper, "用户表", response, baseMapper);
    }
}
