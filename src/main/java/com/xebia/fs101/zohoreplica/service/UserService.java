package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.UserSearchResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.exception.FileNotUploadException;
import com.xebia.fs101.zohoreplica.exception.UserNotFoundException;
import com.xebia.fs101.zohoreplica.exception.WrongPasswordException;
import com.xebia.fs101.zohoreplica.model.Birthday;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import com.xebia.fs101.zohoreplica.utility.FileUtility;
import com.xebia.fs101.zohoreplica.utility.PhotoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.USER_NAME_INVALID;
import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.USER_NAME_VALID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(UserRequest userRequest) {

        return userRepository.save(userRequest.toUser(passwordEncoder));
    }

    public User findById(UUID id) {

        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User id is not " +
                                                        "valid"));
    }

    public User findByName(String username) {

        return userRepository.findByUsername(username);
    }

    public User findByName(String username, String oldPassword) {

        User oldUser = userRepository.findByUsername(username);
        if (oldUser == null)
            throw new UserNotFoundException("User not found");
        if (!passwordEncoder.matches(oldPassword, oldUser.getPassword()))
            throw new WrongPasswordException("Password not matched");
        return oldUser;
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public User changePassword(User user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public String follow(User requestUser, User targetUser) {

        if (!isFollower(requestUser, targetUser)) {
            increaseFollower(requestUser, targetUser);
            increaseFollowing(requestUser, targetUser);
            return "Followed success";
        }
        return "Already following";
    }

    public String unfollow(User requestUser, User targetUser) {

        if (isFollower(requestUser, targetUser)) {
            decreaseFollowing(requestUser, targetUser);
            decreaseFollower(requestUser, targetUser);
            return "Unfollowed Succes";
        }
        return "You need to follow him first";
    }

    private void increaseFollower(User requestUser, User targetUser) {

        Set<String> followers = targetUser.getFollowers();
        long followersCount = targetUser.getFollowersCount();
        followers.add(requestUser.getUsername());
        targetUser.setFollowers(followers);
        targetUser.setFollowersCount(++followersCount);
        userRepository.save(targetUser);
    }

    private void increaseFollowing(User requestUser, User targetUser) {

        Set<String> following = requestUser.getFollowing();
        long followingCount = requestUser.getFollowingCount();
        following.add(targetUser.getUsername());
        requestUser.setFollowing(following);
        requestUser.setFollowingCount(++followingCount);
        userRepository.save(requestUser);
    }

    private void decreaseFollower(User requestUser, User targetUser) {

        Set<String> followers = targetUser.getFollowers();
        long followersCount = targetUser.getFollowersCount();
        followers.remove(requestUser.getUsername());
        targetUser.setFollowers(followers);
        targetUser.setFollowersCount(--followersCount);
        userRepository.save(targetUser);
    }

    private void decreaseFollowing(User requestUser, User targetUser) {

        Set<String> following = requestUser.getFollowing();
        long followingCount = requestUser.getFollowingCount();
        following.remove(targetUser.getUsername());
        requestUser.setFollowing(following);
        requestUser.setFollowingCount(--followingCount);
        userRepository.save(requestUser);
    }

    private boolean isFollower(User requestUser, User targetUser) {

        return targetUser.getFollowers()
                .stream()
                .anyMatch(e -> e.equals(requestUser.getUsername()));
    }

    public List<Birthday> allBirthdays() {

        List<Birthday> allBirthDays = new ArrayList<>();
        try {
            allBirthDays = FileUtility.getAllBirthDays();
            return allBirthDays;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allBirthDays;
    }

    // @Scheduled(cron = "")
    public void calculateBirthdays() {

        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        List<Birthday> allBirthdays = userRepository.allBirthday(month, dayOfMonth)
                .stream()
                .map(User::getBirthdayDetails)
                .collect(Collectors.toList());
        try {
            FileUtility.saveBirthdayDetails(allBirthdays);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean validateUsername(String username) {

        if (username == null || username.equals(""))
            return USER_NAME_INVALID;
        User user = userRepository.findByUsername(username);
        if (user == null)
            return USER_NAME_VALID;
        else
            return USER_NAME_INVALID;
    }

    public List<UserSearchResponse> searchByName(String keyword) throws IOException {

        List<Object[]> matchedData = userRepository.search(keyword.toLowerCase());
        List<UserSearchResponse> userSearchResponseList = new ArrayList<>();
        for (Object[] data : matchedData) {
            userSearchResponseList.add(
                    new UserSearchResponse(UUID.fromString(String.valueOf(data[0])),
                                           String.valueOf(data[1]), PhotoUtility.getBytes(data[2])));
        }
        return userSearchResponseList;
    }

    public User uploadPhoto(User user, MultipartFile file) throws IOException {

        if (file.isEmpty())
            throw new FileNotUploadException("Empty file can't be uploaded");
        byte[] photo = Objects.requireNonNull(file.getBytes());
        user.setPhoto(photo);
        return userRepository.save(user);
    }

    public User deletePhoto(User user) {

        user.setPhoto(null);
        return userRepository.save(user);
    }
}
