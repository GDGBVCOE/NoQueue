package Models;



/**
 * Created by vaibhavchellani on 9/28/17.
 */

public class Token {

    public int token_status;
    public int uid;
    public String time_of_generation;
    public int token_no=0;

    public Token(int token_no) {
        this.token_status = 1;
        //get user id from shared preference
        this.uid = 0;
        Long tsLong = System.currentTimeMillis()/1000;
        time_of_generation= tsLong.toString();

        this.token_no = token_no;
    }

    public int getToken_status() {
        return token_status;
    }

    public void setToken_status(int token_status) {
        this.token_status = token_status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public int getToken_no() {
        return token_no;
    }

    public void setToken_no(int token_no) {
        this.token_no = token_no;
    }
}
