package com.fpt.OnlineQuiz.utils;

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), Constants.STRING_EMPTY);
    }
}
