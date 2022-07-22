package com.chen.milliondataexport.export.service;

import com.chen.milliondataexport.export.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author MaybeBin
 * @since 2022-07-22
 */
public interface UserService extends IService<User> {

    void dataExport(HttpServletResponse response) throws IOException;
}
