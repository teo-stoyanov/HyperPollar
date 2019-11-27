package orgprimeholding.repository;

public interface Repository<T> {
    int insert(T item);

    T get(int id);
}
