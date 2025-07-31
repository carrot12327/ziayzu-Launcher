package com.minecraft.launcher;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import java.io.File;

public class MinecraftApplication extends Application {
    private static Context context;
    public static File MINECRAFT_DIR;
    
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        
        // Initialize Minecraft directory
        MINECRAFT_DIR = new File(Environment.getExternalStorageDirectory(), ".minecraft");
        if (!MINECRAFT_DIR.exists()) {
            MINECRAFT_DIR.mkdirs();
        }
        
        // Create subdirectories
        createDirectories();
    }
    
    private void createDirectories() {
        new File(MINECRAFT_DIR, "versions").mkdirs();
        new File(MINECRAFT_DIR, "assets").mkdirs();
        new File(MINECRAFT_DIR, "libraries").mkdirs();
        new File(MINECRAFT_DIR, "natives").mkdirs();
        new File(MINECRAFT_DIR, "saves").mkdirs();
        new File(MINECRAFT_DIR, "resourcepacks").mkdirs();
        new File(MINECRAFT_DIR, "mods").mkdirs();
    }
    
    public static Context getAppContext() {
        return context;
    }
}