package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

public class EmployeeSignIn extends ApiResponse {
    private String employeeId;

    public String getEmployeeId() {
        return this.employeeId;
    }

    public EmployeeSignIn setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    private String password;

    public String getPassword() {
        return this.password;
    }

    public EmployeeSignIn setPassword(final String password) {
        this.password = password;
        return this;
    }

    public EmployeeSignIn() {
        this.employeeId = StringUtils.EMPTY;
        this.password = StringUtils.EMPTY;
    }
}
