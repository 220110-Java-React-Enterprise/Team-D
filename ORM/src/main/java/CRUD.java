public interface CRUD<T> {
    public T create(T t);
    public T read(T t, String str);
    public T update(T t);
    public void delete(T t, Integer id);
}
