package com.demo.socialnetwork.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UserAuthorityId implements Serializable {

    private String username;
    private String authority;

    public UserAuthorityId() {
    }

    public UserAuthorityId(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthorityId userAuthorityId = (UserAuthorityId) o;
        return username.equals(userAuthorityId.username) &&
        		authority.equals(userAuthorityId.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }
}