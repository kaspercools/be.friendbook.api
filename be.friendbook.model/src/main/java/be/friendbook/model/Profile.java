package be.friendbook.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kaspercools
 */
public class Profile {
    
    @NotNull(message ="Username is a manditory field")
    @Size(min = 5, max = 32, message = "Username must be between 5 and 32 characters long")
    private String username;
    
    @NotNull(message ="Name is a manditory field")
    @Size(min = 3, message = "Name must at least be 3 characters long")
    private String name;
    
    @NotNull(message ="Surname is a manditory field")
    @Size(min = 3, message = "Surname must at least be 3 characters long")
    private String surname;
    
    @Past
    private Date dob;
   
    private int age;
    
    private Set<Profile> friends;

    public Profile(String username, String name, String lastName, Date dob) {
        this();
        
        this.username = username;
        this.name = name;
        this.surname = lastName;
        this.friends = new HashSet<>();
        this.dob=dob;
    }
    

    public Profile() {
        this.friends=new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<Profile> getFriends() {
        return friends;
    }

    public void setFriends(Set<Profile> friends) {
        this.friends = friends;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("{ username:%s, name:%s }",this.getUsername(),this.getName());
    }
}
