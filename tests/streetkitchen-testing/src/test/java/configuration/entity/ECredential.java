package configuration.entity;

public class ECredential {
    private String username;
    private String password;
    private String email;
    private String euconsent_v2;

    public ECredential() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEuconsent_v2() {
        return euconsent_v2;
    }

    public void setEuconsent_v2(String euconsent_v2) {
        this.euconsent_v2 = euconsent_v2;
    }

    @Override
    public String toString() {
        return "ECredential{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + ", euconsent_v2='" + euconsent_v2 + '\'' + '}';
    }
}
