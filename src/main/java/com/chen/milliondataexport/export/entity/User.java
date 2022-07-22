package com.chen.milliondataexport.export.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author MaybeBin
 * @since 2022-07-22
 */
@Data
@TableName("t_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty("唯一uid")
    @TableId("uid")
    private String uid;

    @ExcelProperty("用户名")
    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String userName;

    @ExcelIgnore
    @ApiModelProperty("密码")
    @TableField("pass_word")
    private String passWord;

    @ExcelProperty("性别")
    @ApiModelProperty("性别(1:男0:女)")
    @TableField("sex")
    private String sex;

    @ExcelProperty("个人头像")
    @ApiModelProperty("个人头像")
    @TableField("avatar")
    private String avatar;

    @ExcelProperty("邮箱")
    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("出生年月日")
    @ApiModelProperty("出生年月日")
    @TableField("birthday")
    private Date birthday;

    @ExcelProperty("手机")
    @ApiModelProperty("手机")
    @TableField("mobile")
    private String mobile;

    @ExcelProperty("自我简介")
    @ApiModelProperty("自我简介最多150字")
    @TableField("summary")
    private String summary;

    @ExcelProperty("登录次数")
    @ApiModelProperty("登录次数")
    @TableField("login_count")
    private Integer loginCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("最后登录时间")
    @ApiModelProperty("最后登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

    @ExcelProperty("最后登录IP")
    @ApiModelProperty("最后登录IP")
    @TableField("last_login_ip")
    private String lastLoginIp;

    @ExcelIgnore
    @ApiModelProperty("是否删除，1表示已删除")
    @TableField("is_delete")
    private String isDelete;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("创建时间")
    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("更新时间")
    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ExcelProperty("昵称")
    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ExcelProperty("ip来源")
    @ApiModelProperty("ip来源")
    @TableField("ip_source")
    private String ipSource;

    @ExcelProperty("浏览器")
    @ApiModelProperty("浏览器")
    @TableField("browser")
    private String browser;

    @ExcelProperty("操作系统")
    @ApiModelProperty("操作系统")
    @TableField("os")
    private String os;

    @ExcelProperty("是否开启邮件通知")
    @ApiModelProperty("是否开启邮件通知 1:开启 0:关闭")
    @TableField("start_email_notification")
    private String startEmailNotification;

    @ExcelProperty("用户标签")
    @ApiModelProperty("用户标签：0：普通用户，1：管理员，2：博主 等")
    @TableField("user_tag")
    private String userTag;

}
