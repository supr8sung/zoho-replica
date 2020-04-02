package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.exception.UserNotFoundException;
import com.xebia.fs101.zohoreplica.exception.WrongPasswordException;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
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
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User id is not " +
                "valid"));
    }

    public User findByName(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByName(User user, String oldPassword) {
        User oldUser = userRepository.findByUsername(user.getUsername());
        if (oldUser == null)
            throw new UsernameNotFoundException("User not found");
        if (!passwordEncoder.matches(oldPassword, oldUser.getPassword()))
            throw new WrongPasswordException("Password not matched");
        return user;
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

    public void allBirthdays() {
        userRepository.findAll();
    }
}
