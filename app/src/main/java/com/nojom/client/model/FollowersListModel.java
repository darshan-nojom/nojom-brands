package com.nojom.client.model;

public class FollowersListModel extends GeneralModel {
    public int followersLeave;
    public String followers;
    public boolean isSelected;

    public FollowersListModel(int followersLeave, String followers) {
        this.followersLeave = followersLeave;
        this.followers = followers;
    }
}
