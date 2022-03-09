package com.example.ichat.Model;

public class User {

    String UserName,UserPhone,UserEmail,UserId,UserPassword,UserCoverPic,UserProfilePic;

    public User() {
    }

    public User(String userName, String userPhone, String userEmail, String userId, String userPassword, String userCoverPic, String userProfilePic) {
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
        UserId = userId;
        UserPassword = userPassword;
        UserCoverPic = userCoverPic;
        UserProfilePic = userProfilePic;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserCoverPic() {
        return UserCoverPic;
    }

    public void setUserCoverPic(String userCoverPic) {
        UserCoverPic = userCoverPic;
    }

    public String getUserProfilePic() {
        return UserProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        UserProfilePic = userProfilePic;
    }

}
