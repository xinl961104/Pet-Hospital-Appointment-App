package com.comp90018.drpet;

public class Pet {
    private String petID;
    private String category;
    private String breed;
    private String petAge;
    private String comment;
    private String ownerID;
    private String petName;
    private String imageurl;
    public Pet(){

    }

    public Pet(String petID, String category, String breed, String petAge, String comment, String ownerID, String petName, String imageurl) {
        this.petID = petID;
        this.category = category;
        this.breed = breed;
        this.petAge = petAge;
        this.comment = comment;
        this.ownerID = ownerID;
        this.petName = petName;
        this.imageurl = imageurl;
    }
    
    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
