package by.epam.project.model.entities;



public class User {

    private String email;
    private String password;
    private int status;


    public User(String name, String password) {
        this.email = name;
        this.password = password;
        this.status = 0;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "email = " + email + ", password = " + password + ", status = " + status + ".";
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
        return status == user.getStatus();
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0) + status;
        return result;
    }


}
