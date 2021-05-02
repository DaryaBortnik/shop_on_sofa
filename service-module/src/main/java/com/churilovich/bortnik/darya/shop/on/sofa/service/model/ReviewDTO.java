package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import java.time.LocalDate;

public class ReviewDTO {
    private Long id;
    private String description;
    private LocalDate date;
    private boolean isShown;
    private UserInformationDTO userInformationDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public UserInformationDTO getUserInformationDTO() {
        return userInformationDTO;
    }

    public void setUserInformationDTO(UserInformationDTO userInformationDTO) {
        this.userInformationDTO = userInformationDTO;
    }
}
