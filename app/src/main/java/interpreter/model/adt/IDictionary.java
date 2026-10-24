package interpreter.model.adt;

import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key);
    void put(K key, V value);
    void update(K key, V value);
    Map<K, V> getContent();
    Set<K> keySet();
}
