package by.epam.project.model.entity;



public class User {

    private String email;
    private String password;
    private int id_passport;
    private int status;
    private int role;


    /**
     * Default values:
     * status = 0 (in the process of registration)
     * role = 0 (user)
     * id_passport = -1
     * */
    public User(String name, String password) {
        this.email = name;
        this.password = password;
        this.id_passport = -1;
        this.status = 0;
        this.role = 0;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getIdPassport() {
        return id_passport;
    }

    public void setIdPassport(int id_passport) {
        this.id_passport = id_passport;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "email = " + email + ", password = " + password
                + ", id_passport = " + id_passport
                + ", status = " + (status == 0 ? "in the process of registration" : "registered")
                + ", role = " + (role == 0 ? "user" : "admin") + ".";
    }


   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (password != null ? password.equals(user.password) : user.password == null)
            return false;
        return status == user.getStatus()
                && role == user.getRole()
                && id_passport == user.getIdPassport();
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0)
                + status + role + (id_passport+1);
        return result;
    }


}
