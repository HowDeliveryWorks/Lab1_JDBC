package Data;

import java.io.Serializable;

/**
 * Created by Игорь on 20.03.2017.
 */
public class User implements Serializable
{
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        return password.equals(user.password);

    }

    @Override
    public int hashCode()
    {
        int result = login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    private String login;
    private String password;

    public User(){}

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString(){
        return "Login: " + login + "; Password: " + password;
    }
}
