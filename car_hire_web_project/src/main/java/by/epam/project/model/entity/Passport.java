package by.epam.project.model.entity;

public class Passport {

    private int id; // auto increment
    private String name;
    private String surname;
    private String birthday;
    private String identification_number;


    public Passport(String name, String surname, String birthday, String identification_number){
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.identification_number = identification_number;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(String identification_number) {
        this.identification_number = identification_number;
    }


    @Override
    public String toString() {
        return "name = " + name
                + ",\nsurname = " + surname
                + ",\nbirthday = " + birthday
                + ",\nidentification_number = " + identification_number
                ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passport p = (Passport) o;

        if(name.equals(p.name) && surname.equals(p.surname)
                && birthday.equals(p.birthday) && identification_number.equals(p.identification_number)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result
                + (name != null ? name.hashCode() : 0)
                + (surname != null ? surname.hashCode() : 0)
                + (birthday != null ? birthday.hashCode() : 0)
                + (identification_number != null ? identification_number.hashCode() : 0);
        return result;
    }


}
