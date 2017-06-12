package com.ausy.yu.bonjourausy.models;

/**
 * Created by yukaiwen on 19/04/2017.
 */

public class ManagerRdvItem {

    private String candidateName, managerName, heurePrevu;

    public ManagerRdvItem() {}

    public ManagerRdvItem(String candidateName, String managerName, String heurePrevu) {
        this.candidateName = candidateName;
        this.managerName = managerName;
        this.heurePrevu = heurePrevu;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getHeurePrevu() {
        return heurePrevu;
    }

    public void setHeurePrevu(String heurePrevu) {
        this.heurePrevu = heurePrevu;
    }
}
