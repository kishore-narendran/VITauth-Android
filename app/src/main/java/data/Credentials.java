package data;

public class Credentials {
    private String employeeId;
    private String password;

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPassword() {
        return password;
    }

    public Credentials(String employeeId, String password) {

        this.employeeId = employeeId;
        this.password = password;
    }
}
