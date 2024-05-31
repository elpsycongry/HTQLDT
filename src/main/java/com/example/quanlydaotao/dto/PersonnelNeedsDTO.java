package com.example.quanlydaotao.dto;

public class PersonnelNeedsDTO {
    private int personnelNeeds;
    private int awaitingApproval;
    private int approved;
    private int handedOver;
    public PersonnelNeedsDTO() {}
    public PersonnelNeedsDTO(int personnelNeeds, int awaitingApproval, int approved, int handedOver) {
        this.personnelNeeds = personnelNeeds;
        this.awaitingApproval = awaitingApproval;
        this.approved = approved;
        this.handedOver = handedOver;
    }

    public int getPersonnelNeeds() {
        return personnelNeeds;
    }

    public void setPersonnelNeeds(int personnelNeeds) {
        this.personnelNeeds = personnelNeeds;
    }

    public int getAwaitingApproval() {
        return awaitingApproval;
    }

    public void setAwaitingApproval(int awaitingApproval) {
        this.awaitingApproval = awaitingApproval;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getHandedOver() {
        return handedOver;
    }

    public void setHandedOver(int handedOver) {
        this.handedOver = handedOver;
    }
}
