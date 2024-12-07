package com.natcu.Job.Portl.services;

import com.natcu.Job.Portl.entity.UsersType;
import com.natcu.Job.Portl.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {
    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;


    }
    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
