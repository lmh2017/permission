package com.defu.permission.repo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/22
 * time: 11:26
 */
@Data
@Entity
@Table(name = "xgl_emp")
@Proxy(lazy = false)
public class EmpDO {

    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "password")
    private String password;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "emp_phone")
    private String empPhone;

    private String empEmail;

    @Column(columnDefinition = "char")
    private String status;

    @Column(columnDefinition = "char")
    private String empMark;

    private Integer roleId;

    private Integer deptId;

    private Integer userId;

    @Column(columnDefinition = "char")
    protected String enableFlag = "Y";

    protected String createBy;

    protected Date createTime = new Date();

    protected String updateBy;

    protected Date updateTime;





}
