package orgprimeholding.repository;

public interface Repository<T> {
    void insert(T item);

    int getId();
}
