package uk.co.adaptivelogic.forgery;

public class GuiceForgerRegistryTest extends ForgerRegistryTest {
    public ForgerRegistry getForgerRegistry() {
        return new GuiceForgerRegistry();
    }
}
