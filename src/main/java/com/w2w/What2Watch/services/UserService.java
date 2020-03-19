package com.w2w.What2Watch.services;

import com.w2w.What2Watch.exceptions.UserNotFoundException;
import com.w2w.What2Watch.models.Genre;
import com.w2w.What2Watch.models.SpokenLanguage;
import com.w2w.What2Watch.models.User;
import com.w2w.What2Watch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    //TODO : need to handle exceptions
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity Register(User userDetails) {
        //TODO : take user preferences and then save to DB
        userRepository.save(userDetails);
        return Login(userDetails);
    }

    public ResponseEntity Login(User userDetails) {
        User user = userRepository.findByEmail(userDetails.email);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    public boolean IsRegistered(User details) {
        User userDetails = userRepository.findByEmail(details.getEmail());
        if (userDetails == null){
            return false;
        } else
            return true;
    }

    public User getUserByGivenEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException("User with email \"" + email + "\" not found");
        return user;
    }

    public User addUserGenreByEmail(String email, Genre genre) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserNotFoundException("User with email \"" + email + "\" not found");
        List<Genre>genres= user.getGenres();
        if(!genres.contains(genre)) {
            genres.add(genre);
        }
        user.setGenres(genres);
        return userRepository.save(user);

    }

    public User deleteUserGenreByEmail(String email, Genre genre) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserNotFoundException("User with email \"" + email + "\" not found");
        List<Genre> genres = user.getGenres();
        if(genres !=null  && genres.contains(genre)) {
            genres.remove(genre);
        }
        user.setGenres(genres);
        return userRepository.save(user);

    }

    public User addUserLanguageByEmail(String email, SpokenLanguage spokenLanguage) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserNotFoundException("User with email \"" + email + "\" not found");
        List<SpokenLanguage> spokenLanguages= user.getLanguages();
        if(!spokenLanguages.contains(spokenLanguage)) {
            spokenLanguages.add(spokenLanguage);
        }
        user.setLanguages(spokenLanguages);
        return userRepository.save(user);

    }
    public User deleteUserLanguageByEmail(String email, SpokenLanguage spokenLanguage) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserNotFoundException("User with email \"" + email + "\" not found");
        List<SpokenLanguage> spokenLanguages= user.getLanguages();
        if(spokenLanguages !=null  && spokenLanguages.contains(spokenLanguage)) {
            spokenLanguages.remove(spokenLanguage);
        }
        user.setLanguages(spokenLanguages);
        return userRepository.save(user);

    }



}
