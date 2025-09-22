package org.adaschool.api.repository.user;

public class UserDto {
    private String name;
    private String lastName;
    private String email;
    private String phone;

    public UserDto() {}
    public UserDto(String name, String lastName, String email, String phone) {
        this.name = name; this.lastName = lastName; this.email = email; this.phone = phone;
    }

    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
