package uk.co.adaptivelogic.forgery;

public interface Forger<T> extends Serviceable {
	T forge();
}
