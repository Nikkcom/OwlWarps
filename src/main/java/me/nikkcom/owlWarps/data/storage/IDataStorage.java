package me.nikkcom.owlWarps.data.storage;

import java.util.List;
import java.util.UUID;

public interface IDataStorage<T> {
    /**
     * Saves the given object to the data storage.
     *
     * @param object The object to save.
     */
    void save(T object);

    /**
     * Loads the object associated with the given ID.
     *
     * @param object The object to load.
     *
     * @return The loaded object or null if not found.
     */
    T load(T object);

    /**
     * Deletes the object associated with the given ID from the data storage.
     *
     * @param object The object to delete.
     */
    void delete(T object);

    /**
     * Loads all objects from the data storage.
     *
     * @return A list of all objects.
     */
    List<T> loadAll();

    /**
     * Saves all objects in the provided list to the data storage.
     *
     * @param objects The list of objects to save.
     */
    void saveAll(List<T> objects);
}
