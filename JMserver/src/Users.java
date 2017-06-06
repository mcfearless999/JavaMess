/**
 * Created by chrism on 5/25/17.
 */




class user
{
    String userName_;
    String passWord_;
    int status = 0;

    user()
    {
        userName_ = null;
        passWord_ = null;
    }

    user(String userName, String passWord)
    {
        userName_ = userName;
        passWord_ = passWord;
    }
    boolean setStatus(int newStat)
    {
        status= newStat;
        return true;
    }
    void display()
    {
        System.out.println(userName_);
        System.out.println(passWord_);
        System.out.println(status);
    }
}
