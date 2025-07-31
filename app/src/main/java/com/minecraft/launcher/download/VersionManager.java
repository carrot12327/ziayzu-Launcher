package com.minecraft.launcher.download;

import android.content.Context;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minecraft.launcher.models.MinecraftVersion;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VersionManager {
    private static final String VERSION_MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private Context context;
    private Gson gson;
    
    public interface VersionCallback {
        void onSuccess(List<MinecraftVersion> versions);
        void onError(String error);
    }
    
    public VersionManager(Context context) {
        this.context = context;
        this.gson = new Gson();
    }
    
    public void getVersions(VersionCallback callback) {
        new GetVersionsTask(callback).execute();
    }
    
    private class GetVersionsTask extends AsyncTask<Void, Void, List<MinecraftVersion>> {
        private VersionCallback callback;
        private String errorMessage;
        
        public GetVersionsTask(VersionCallback callback) {
            this.callback = callback;
        }
        
        @Override
        protected List<MinecraftVersion> doInBackground(Void... voids) {
            try {
                URL url = new URL(VERSION_MANIFEST_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    return parseVersionManifest(response.toString());
                } else {
                    errorMessage = "HTTP Error: " + responseCode;
                    return null;
                }
            } catch (Exception e) {
                errorMessage = "Network error: " + e.getMessage();
                return null;
            }
        }
        
        @Override
        protected void onPostExecute(List<MinecraftVersion> versions) {
            if (versions != null && callback != null) {
                callback.onSuccess(versions);
            } else if (callback != null) {
                callback.onError(errorMessage != null ? errorMessage : "Unknown error");
            }
        }
    }
    
    private List<MinecraftVersion> parseVersionManifest(String json) {
        try {
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> manifest = gson.fromJson(json, type);
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> versionsList = (List<Map<String, Object>>) manifest.get("versions");
            
            List<MinecraftVersion> versions = new ArrayList<>();
            
            for (Map<String, Object> versionData : versionsList) {
                String id = (String) versionData.get("id");
                String type = (String) versionData.get("type");
                String url = (String) versionData.get("url");
                
                // Only include release and snapshot versions
                if ("release".equals(type) || "snapshot".equals(type)) {
                    MinecraftVersion version = new MinecraftVersion(id, type, url);
                    versions.add(version);
                    
                    // Limit to recent versions to avoid overwhelming the list
                    if (versions.size() >= 50) break;
                }
            }
            
            return versions;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public void downloadVersion(MinecraftVersion version, DownloadCallback callback) {
        new DownloadVersionTask(version, callback).execute();
    }
    
    public interface DownloadCallback {
        void onProgress(int progress);
        void onSuccess();
        void onError(String error);
    }
    
    private class DownloadVersionTask extends AsyncTask<Void, Integer, Boolean> {
        private MinecraftVersion version;
        private DownloadCallback callback;
        private String errorMessage;
        
        public DownloadVersionTask(MinecraftVersion version, DownloadCallback callback) {
            this.version = version;
            this.callback = callback;
        }
        
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Simulate download progress
                for (int i = 0; i <= 100; i += 10) {
                    Thread.sleep(200);
                    publishProgress(i);
                }
                return true;
            } catch (Exception e) {
                errorMessage = "Download failed: " + e.getMessage();
                return false;
            }
        }
        
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (callback != null) {
                callback.onProgress(values[0]);
            }
        }
        
        @Override
        protected void onPostExecute(Boolean success) {
            if (success && callback != null) {
                callback.onSuccess();
            } else if (callback != null) {
                callback.onError(errorMessage != null ? errorMessage : "Unknown error");
            }
        }
    }
}