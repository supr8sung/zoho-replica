package com.xebia.fs101.zohoreplica.api.response;

public class FollowResponse {
    private long followers;
    private long following;

    public FollowResponse(long followers, long following) {
        this.followers = followers;
        this.following = following;
    }

    public long getFollowers() {
        return followers;
    }

    public long getFollowing() {
        return following;
    }
}
