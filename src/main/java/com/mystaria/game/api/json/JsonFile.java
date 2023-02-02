package com.mystaria.game.api.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Giovanni on 1/29/2023
 * <p>
 * A file object that uses Gson to read & write objects from/to a Json file.
 */
public class JsonFile {

    public static Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();
    public static Gson GSON = new Gson();

    private final File file;
    private final String fileName;
    private final boolean prettyOut;

    public JsonFile(String path, String fileName, boolean prettyOut) {
        this.file = new File(path, fileName);
        this.fileName = fileName;
        this.prettyOut = prettyOut;
    }

    /**
     * Writes an object to this file in Json. This operation overwrites the current contents of the file.
     *
     * @param serializable The object to write.
     */
    public void writeObject(Object serializable) throws IOException {
        String writeOut = prettyOut ? GSON_PRETTY.toJson(serializable) : GSON.toJson(serializable);
        Files.writeString(file.toPath(), writeOut);
    }

    /**
     * Reads the object from this Json file.
     *
     * @param type The type class of the object.
     */
    public <T> T readObject(Class<T> type) throws IOException {
        String readIn = Files.readString(file.toPath());
        return type.cast(prettyOut ? GSON_PRETTY.fromJson(readIn, type) : GSON.fromJson(readIn, type));
    }

    /**
     * Creates a new file.
     *
     * @param override Whether the file should be overriden if it already exists
     * @return Success or not
     */
    public boolean create(boolean override) throws IOException {
        if (exists() && !override) return false;
        return file.createNewFile();
    }

    /**
     * Deletes this JSON file
     */
    public boolean delete() throws FileNotFoundException {
        if (!exists()) throw new FileNotFoundException(fileName);
        return file.delete();
    }

    /**
     * Returns whether this JSON file exists on disk
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * Returns this file's {@link Path}.
     */
    public Path getPath() {
        return file.toPath();
    }
}
