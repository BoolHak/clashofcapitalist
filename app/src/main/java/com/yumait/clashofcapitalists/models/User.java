package com.yumait.clashofcapitalists.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class User implements Serializable {

    private String userId;
    private String userName;
    private String userMail;
    private String userPhoto;
    private String userPassword;
    private String userToken;
    private int userLevel;
    private int userExperience;
    private String userCountry;
    private int userGoldValue;
    private double userMoneyValue;
    private double userCogsValue;
    private double userFoodValue;
    private double userTechValue;

    private List<Factory> userFactories;
    private List<Colony> userColonies;
    private List<ArmyFactory> userArmyFactories;
    private List<Upgrade> userUpgrades;
    private Stock userStock;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(int userExperience) {
        this.userExperience = userExperience;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public int getUserGoldValue() {
        return userGoldValue;
    }

    public void setUserGoldValue(int userGoldValue) {
        this.userGoldValue = userGoldValue;
    }

    public double getUserCogsValue() {
        return userCogsValue;
    }

    public void setUserCogsValue(double userCogsValue) {
        this.userCogsValue = userCogsValue;
    }

    public double getUserFoodValue() {
        return userFoodValue;
    }

    public void setUserFoodValue(double userFoodValue) {
        this.userFoodValue = userFoodValue;
    }

    public double getUserTechValue() {
        return userTechValue;
    }

    public void setUserTechValue(double userTechValue) {
        this.userTechValue = userTechValue;
    }

    public double getUserMoneyValue() {
        return userMoneyValue;
    }

    public void setUserMoneyValue(double userMoneyValue) {
        this.userMoneyValue = userMoneyValue;
    }

    public List<Factory> getUserFactories() {
        return userFactories;
    }

    public void setUserFactories(List<Factory> userFactories) {
        this.userFactories = userFactories;
    }

    public List<Colony> getUserColonies() {
        return userColonies;
    }

    public void setUserColonies(List<Colony> userColonies) {
        this.userColonies = userColonies;
    }

    public List<ArmyFactory> getUserArmyFactories() {
        return userArmyFactories;
    }

    public void setUserArmyFactories(List<ArmyFactory> userArmyFactories) {
        this.userArmyFactories = userArmyFactories;
    }

    public List<Upgrade> getUserUpgrades() {
        return userUpgrades;
    }

    public void setUserUpgrades(List<Upgrade> userUpgrades) {
        this.userUpgrades = userUpgrades;
    }

    public Stock getUserStock() {
        return userStock;
    }

    public void setUserStock(Stock userStock) {
        this.userStock = userStock;
    }
}
