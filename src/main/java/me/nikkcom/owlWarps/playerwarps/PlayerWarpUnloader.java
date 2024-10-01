package me.nikkcom.owlWarps.playerwarps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.nikkcom.owlWarps.OwlWarps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerWarpUnloader {

    private static final String PLAYERWARP_DIRECTORY = "./warps/";
    private final Gson gson;

    private final OwlWarps owlWarps;

    public PlayerWarpUnloader(OwlWarps owlWarps) {
        this.owlWarps = owlWarps;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public void unloadAll() {

    }

    public void unloadWarp(PlayerWarp warp) {
       File warpDir = new File(PLAYERWARP_DIRECTORY);
       if (!warpDir.exists()) {
            owlWarps.getLogger().info(
                    String.format("%s directory was not found. Creating the directory...", PLAYERWARP_DIRECTORY));
            boolean bool = warpDir.mkdirs();

            if (!bool) {
                owlWarps.getLogger().severe(
                        String.format("%s directory could not be created.", PLAYERWARP_DIRECTORY));
                return;
            }
           owlWarps.getLogger().info(
                   String.format("%s directory was created.", PLAYERWARP_DIRECTORY));
       }
       String fileName = PLAYERWARP_DIRECTORY + warp.getRawName().toLowerCase() + ".json";
       File warpFile = new File(fileName);

       try (FileWriter writer = new FileWriter(warpFile)) {
            gson.toJson(warp, writer);
            owlWarps.getLogger().info(String.format("Saved warp %s to %s", warp.getRawName(), fileName));
       } catch (IOException e) {
           owlWarps.getLogger().info(String.format("Failed to save warp '%s'.", warp.getRawName()));
           e.printStackTrace();
       }
    }
}


















