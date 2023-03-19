package com.nyanband.university_organizer.controller.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtils {
    private static final String PROFILE_ID = "PROFILE_ID";

    public long getProfileId(HttpSession session) {
        return (long) session.getAttribute(PROFILE_ID);
    }

    public void setProfileId(HttpSession session, long value) {
        session.setAttribute(PROFILE_ID, value);
    }

}
