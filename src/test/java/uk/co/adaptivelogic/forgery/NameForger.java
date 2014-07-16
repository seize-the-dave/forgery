package uk.co.adaptivelogic.forgery;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

@Property("name")
public class NameForger implements Provider<String> {
    private Provider<String> firstName;
    private Provider<String> lastName;
    
    @Inject
    public NameForger(@Named("firstName") Provider<String> firstName, @Named("lastName") Provider<String> lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String get() {
        return firstName.get() + " " + lastName.get();
    }
}
