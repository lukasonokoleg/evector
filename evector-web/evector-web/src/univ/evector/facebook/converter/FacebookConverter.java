package univ.evector.facebook.converter;

public interface FacebookConverter<E, T> {

    E convert(T value);

}
