package org.adaschool.api.repository.user;

public class User {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phone;

    public User() {}
    public User(String id, String name, String lastName, String email, String phone) {
        this.id = id; this.name = name; this.lastName = lastName; this.email = email; this.phone = phone;
    }
    public User(UserDto dto) {
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
