package com.capstone.ttp.responses;

import com.capstone.ttp.entitiy.User;

public class AuthenticationResponse {
        private LoginResponse loginResponse;
        private User authenticatedUser;

        // Constructors, getters, and setters
        public AuthenticationResponse(LoginResponse loginResponse, User authenticatedUser) {
            this.loginResponse = loginResponse;
            this.authenticatedUser = authenticatedUser;
        }

        public LoginResponse getLoginResponse() {
            return loginResponse;
        }

        public void setLoginResponse(LoginResponse loginResponse) {
            this.loginResponse = loginResponse;
        }

        public User getAuthenticatedUser() {
            return authenticatedUser;
        }

        public void setAuthenticatedUser(User authenticatedUser) {
            this.authenticatedUser = authenticatedUser;
        }

}
