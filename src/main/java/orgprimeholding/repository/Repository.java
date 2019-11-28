package orgprimeholding.repository;

public interface Repository<T> {
    Integer insert(T item);

    T get(int id);
}
