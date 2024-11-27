package com.backend.jwt;

public class JwtResponse {
    private String jwtToken;
    private String username;

    public JwtResponse() {
        super();
    }

    public JwtResponse(String jwtToken, String username) {
        super();
        this.jwtToken = jwtToken;
        this.username = username;
    }

    public JwtResponse(Builder builder) {
        this.jwtToken = builder.jwtToken;
        this.username = builder.username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class Builder {
        private String jwtToken;
        private String username;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {}

        public Builder setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(this);
        }
    }
}
