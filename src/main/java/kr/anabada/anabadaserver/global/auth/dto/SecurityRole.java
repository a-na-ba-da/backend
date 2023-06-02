package kr.anabada.anabadaserver.global.auth.dto;

public enum SecurityRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String myRole;

    SecurityRole(final String myRole) {
        this.myRole = myRole;
    }

    @Override
    public String toString() {
        return myRole;
    }
}
