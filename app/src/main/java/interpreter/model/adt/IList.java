package interpreter.model.adt;

import java.util.List;

public interface IList<T> {
    void add(T v);
    T get(int index);
    int size();
    List<T> getList();
    List<T> toList();
}
