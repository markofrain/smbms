package pers.cgq.smbms.dao;

import org.springframework.stereotype.Repository;

import pers.cgq.smbms.pojo.Address;
@Repository
public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
}