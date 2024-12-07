package com.natcu.Job.Portl.services;


import com.natcu.Job.Portl.entity.JobSeekerProfile;
import com.natcu.Job.Portl.entity.RecruiterProfile;
import com.natcu.Job.Portl.entity.Users;
import com.natcu.Job.Portl.repository.JobSeekerProfileRepository;
import com.natcu.Job.Portl.repository.RecruiterProfileRepository;
import com.natcu.Job.Portl.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class UsersService {


    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, RecruiterProfileRepository recruiterProfileRepository, JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.usersRepository = usersRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }





    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public Users addNew(Users users){
        users.setIs_active(true);
        users.setRegistration_date(new Date(System.currentTimeMillis()));

        int userTypeId = users.getUserTypeId().getUserTypeId();

        Users savedUser = usersRepository.save(users);
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }


        return users;
    }
}