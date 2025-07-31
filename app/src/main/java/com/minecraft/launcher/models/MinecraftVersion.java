package com.minecraft.launcher.models;

import java.util.Date;
import java.util.List;

public class MinecraftVersion {
    private String id;
    private String type;
    private String url;
    private Date time;
    private Date releaseTime;
    private MinecraftVersionDetails details;
    
    // Constructor
    public MinecraftVersion(String id, String type, String url) {
        this.id = id;
        this.type = type;
        this.url = url;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    
    public Date getReleaseTime() { return releaseTime; }
    public void setReleaseTime(Date releaseTime) { this.releaseTime = releaseTime; }
    
    public MinecraftVersionDetails getDetails() { return details; }
    public void setDetails(MinecraftVersionDetails details) { this.details = details; }
    
    public static class MinecraftVersionDetails {
        private Arguments arguments;
        private AssetIndex assetIndex;
        private String assets;
        private Downloads downloads;
        private String id;
        private List<Library> libraries;
        private String mainClass;
        private String type;
        
        // Getters and setters
        public Arguments getArguments() { return arguments; }
        public void setArguments(Arguments arguments) { this.arguments = arguments; }
        
        public AssetIndex getAssetIndex() { return assetIndex; }
        public void setAssetIndex(AssetIndex assetIndex) { this.assetIndex = assetIndex; }
        
        public String getAssets() { return assets; }
        public void setAssets(String assets) { this.assets = assets; }
        
        public Downloads getDownloads() { return downloads; }
        public void setDownloads(Downloads downloads) { this.downloads = downloads; }
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public List<Library> getLibraries() { return libraries; }
        public void setLibraries(List<Library> libraries) { this.libraries = libraries; }
        
        public String getMainClass() { return mainClass; }
        public void setMainClass(String mainClass) { this.mainClass = mainClass; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
    
    public static class Arguments {
        private List<String> game;
        private List<String> jvm;
        
        public List<String> getGame() { return game; }
        public void setGame(List<String> game) { this.game = game; }
        
        public List<String> getJvm() { return jvm; }
        public void setJvm(List<String> jvm) { this.jvm = jvm; }
    }
    
    public static class AssetIndex {
        private String id;
        private String sha1;
        private int size;
        private int totalSize;
        private String url;
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getSha1() { return sha1; }
        public void setSha1(String sha1) { this.sha1 = sha1; }
        
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        
        public int getTotalSize() { return totalSize; }
        public void setTotalSize(int totalSize) { this.totalSize = totalSize; }
        
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
    
    public static class Downloads {
        private Download client;
        private Download client_mappings;
        private Download server;
        private Download server_mappings;
        
        public Download getClient() { return client; }
        public void setClient(Download client) { this.client = client; }
        
        public Download getClientMappings() { return client_mappings; }
        public void setClientMappings(Download client_mappings) { this.client_mappings = client_mappings; }
        
        public Download getServer() { return server; }
        public void setServer(Download server) { this.server = server; }
        
        public Download getServerMappings() { return server_mappings; }
        public void setServerMappings(Download server_mappings) { this.server_mappings = server_mappings; }
    }
    
    public static class Download {
        private String sha1;
        private int size;
        private String url;
        
        public String getSha1() { return sha1; }
        public void setSha1(String sha1) { this.sha1 = sha1; }
        
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
    
    public static class Library {
        private Downloads downloads;
        private String name;
        private Rules rules;
        
        public Downloads getDownloads() { return downloads; }
        public void setDownloads(Downloads downloads) { this.downloads = downloads; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Rules getRules() { return rules; }
        public void setRules(Rules rules) { this.rules = rules; }
    }
    
    public static class Rules {
        private String action;
        private Os os;
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public Os getOs() { return os; }
        public void setOs(Os os) { this.os = os; }
    }
    
    public static class Os {
        private String name;
        private String version;
        private String arch;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        
        public String getArch() { return arch; }
        public void setArch(String arch) { this.arch = arch; }
    }
}