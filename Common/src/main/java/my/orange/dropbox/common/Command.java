package my.orange.dropbox.common;

import java.io.Serializable;

public enum Command implements Serializable {

    LOGIN, REGISTER,
    AUTH_SUCCESS, LOGIN_INCORRECT, PASSWORD_INCORRECT, LOGIN_BUSY,
    PUT, DELETE, GET, LIST
}
