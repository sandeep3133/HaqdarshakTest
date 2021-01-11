package com.mudhales.haqdarshak.utils;
public class Constant {
    public interface ServerEndpoint {
        String LOGIN = "ws-login/ws-login.php/"; // Added by SM
    }

    public interface SharedPreferences {
        String LOGGED_IN_USER = "LOGGED_IN_USER";
        String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
        String IS_BLOCKED_TIME = "IS_BLOCKED_TIME";
        String INVALID_LOGIN_COUNT = "INVALID_LOGIN_COUNT";
        //String ALERT_ADD_MOBILE_NUMBER = "ALERT_ADD_MOBILE_NUMBER"; // Added by SM
    }

    public interface Common {
        String SHARED_PREFERENCES = "HAQDARSHAK_SHARED_PREFERENCES";
    }

    public interface IntentExtras {
        String NAV_VIEW_ID = "NAV_VIEW_ID";
        String DATA = "DATA";
    }

    public interface DateFormat {
        String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
        String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd"; //In which you need put here
        String DATE_FORMAT_DD_MM_YY = "dd/MM/yy";
        String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy";
        String DATE_FORMAT_DD_MM_YY_KK_MM_SS = "dd/MM/yy kk:mm:ss";
        String DATE_FORMAT_DD_MMM_YYYY_KK_MM_SS = "dd-MMM-yyyy kk:mm:ss";
    }
}
