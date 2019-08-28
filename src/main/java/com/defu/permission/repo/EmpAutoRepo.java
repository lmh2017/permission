package com.defu.permission.repo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/22
 * time: 11:32
 */
public interface EmpAutoRepo extends JpaRepository<EmpDO,Integer> {

    EmpDO findByLoginName(String cellPhone);

}
