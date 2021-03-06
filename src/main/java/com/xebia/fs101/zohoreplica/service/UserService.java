package com.xebia.fs101.zohoreplica.service;

import com.xebia.fs101.zohoreplica.api.request.UserRequest;
import com.xebia.fs101.zohoreplica.api.response.UserViewResponse;
import com.xebia.fs101.zohoreplica.entity.User;
import com.xebia.fs101.zohoreplica.exception.FileNotUploadException;
import com.xebia.fs101.zohoreplica.exception.UserNotFoundException;
import com.xebia.fs101.zohoreplica.exception.WrongPasswordException;
import com.xebia.fs101.zohoreplica.model.Birthday;
import com.xebia.fs101.zohoreplica.model.Clients;
import com.xebia.fs101.zohoreplica.model.UserSearch;
import com.xebia.fs101.zohoreplica.repository.UserRepository;
import com.xebia.fs101.zohoreplica.utility.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.USER_NAME_INVALID;
import static com.xebia.fs101.zohoreplica.api.constant.ApplicationConstant.USER_NAME_VALID;
import static com.xebia.fs101.zohoreplica.utility.OtpUtility.generateOtp;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private CacheManager cacheManager;

    public User add(UserRequest userRequest, String username) {

        Clients company = userRepository.findByUsername(username).getCompany();
        return userRepository.save(userRequest.toUser(passwordEncoder, company));
    }

    public User findById(Long id) {

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

    @Scheduled(cron = "0 59 23 * * * ")
    public void clearCache() {


        cacheManager.getCacheNames().forEach(
                e -> Objects.requireNonNull(cacheManager.getCache(e)).clear());
        calculateBirthdays();
    }

    @Cacheable("birthdays")
    public List<Birthday> calculateBirthdays() {


        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        List<Birthday> allBirthdays = userRepository.allBirthdaysOn(month, dayOfMonth)
                .stream()
                .map(User::getBirthdayDetails)
                .collect(Collectors.toList());
        return allBirthdays;
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

    public List<UserViewResponse> searchByName(String keyword) {

        List<UserViewResponse> matchedUserList = userRepository.search(
                keyword.toLowerCase()).stream().map(
                User::toUserViewResponse).collect(Collectors.toList());
        matchedUserList.removeIf(value -> value.getUsername().startsWith("admin"));
        return matchedUserList;
    }

    public User uploadPhoto(User user, MultipartFile file) throws IOException {

        if (file.isEmpty())
            throw new FileNotUploadException("Empty file can't be uploaded");
        byte[] photo = Objects.requireNonNull(file.getBytes());
        user.setPhoto(photo);
        return userRepository.save(user);
    }

    public void deletePhoto(User user) {

        user.setPhoto(null);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public List<UserSearch> allReportings(Long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No user found with given id"));
        List<User> allReportings = userRepository.findAllReportings(user.getCity().toString(),
                                                                    user.getDesignation());
        return allReportings.stream().map(
                e -> new UserSearch(e.getId(), e.getFullname(), e.getPhoto())).collect(
                Collectors.toList());
    }

    public User addReportingManager(Long userId, Long managerId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No user found with requested id"));
        User manager = userRepository.findById(managerId).orElseThrow(
                () -> new UserNotFoundException("No manager found with requested id"));
        user.setReportingTo(manager);
        return userRepository.save(user);
    }

    public long sendMail(String name) throws InterruptedException {

        User user = userRepository.findByUsername(name);
        String otp = generateOtp();
        String mailBody = StringUtility.generateOtpBody(otp, user.getFullname());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Callable callable = new GmailService(countDownLatch, user.getUsername(), mailBody);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(callable);
        countDownLatch.await();
        return userTokenService.save(user.getUsername(), otp);
    }
}
