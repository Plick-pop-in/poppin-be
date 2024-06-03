package com.soongsil.poppin.heart.application.response;

public class PostHeart {
    private final Boolean state;
    private final Long likeCount;

    public PostHeart(Boolean state, Long likeCount) {
        this.state = state;
        this.likeCount = likeCount;
    }

    public Boolean getState() {
        return state;
    }

    public Long getLikeCount() {
        return likeCount;
    }
}
