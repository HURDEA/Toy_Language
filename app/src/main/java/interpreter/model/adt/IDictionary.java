package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void put(K key, V value);
    void update(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;
    Map<K, V> toMap();
    Map<K, V> getContent();
    Set<K> keySet();
}
