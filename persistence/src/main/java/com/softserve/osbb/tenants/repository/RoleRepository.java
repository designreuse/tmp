package com.softserve.osbb.tenants.repository;

import com.softserve.osbb.tenants.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by nazar.dovhyy on 04.07.2016.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    @Query("select r from Role r where r.name = :name")
    public Role findByName(@Param("name") String name);

}
