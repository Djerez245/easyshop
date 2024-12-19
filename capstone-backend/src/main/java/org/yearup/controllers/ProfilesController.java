package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/profile")
@CrossOrigin
public class ProfilesController {
    private ProfileDao profileDao;

    @Autowired
    public ProfilesController(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    // gets a users profile by a userID
    @GetMapping("/{userId}")
    public Profile getByUserId(@PathVariable int userId){
        try {
            return profileDao.getById(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{userId}")
    public void updateProfile(@PathVariable int userId, @RequestBody Profile profile){
        profileDao.update(userId, profile);
    }
}
