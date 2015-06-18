package edu.oregonstate.mist.dropwizardtest.auth

import io.dropwizard.auth.AuthenticationException
import io.dropwizard.auth.Authenticator
import io.dropwizard.auth.basic.BasicCredentials
import com.google.common.base.Optional

public class SimpleAuthenticator implements Authenticator<BasicCredentials, AuthenticatedUser> {
    public Optional<AuthenticatedUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (valid(credentials)) {
            return Optional.of(new AuthenticatedUser(credentials.username))
        }
        return Optional.absent()
    }

    /* HTTP Basic Authentication with predefined credentials. */
    private Boolean valid(BasicCredentials credentials) {
        return ((credentials.password == 'password') && (credentials.username == 'username'))
    }
}
