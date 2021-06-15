package com.revature.models;

import java.util.*;

public enum Role {

    ADMIN(5),
    DEV(4),
    PREMIUM_USER(3),
    BASIC_USER(2),
    LOCKED(1),
    UNAUTHORIZED(0);

    private int permissionLevel;

    Role(int permissionLevel){
        this.permissionLevel = permissionLevel;
    }

    public static int valueOf(Role role){
        return role.permissionLevel;
    }

    public static List<Role> getRole(int authorizationLevel){
        List<Role> retList = new ArrayList<>();
        switch(authorizationLevel){
            case 5:
                retList.add(ADMIN);
            case 4:
                retList.add(DEV);
            case 3:
                retList.add(PREMIUM_USER);
            case 2:
                retList.add(BASIC_USER);
                break;
            case 1:
                retList.add(LOCKED);
            default:
                retList.add(UNAUTHORIZED);
        }
        return retList;
    }
}
