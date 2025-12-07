package com.example.JobFinderApi.Repository;

import com.example.JobFinderApi.Pojo.Entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {
}
