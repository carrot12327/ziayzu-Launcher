package com.minecraft.launcher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.minecraft.launcher.download.VersionManager;
import com.minecraft.launcher.models.MinecraftVersion;
import com.minecraft.launcher.adapters.VersionAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private RecyclerView versionsRecyclerView;
    private VersionAdapter versionAdapter;
    private List<MinecraftVersion> versions;
    private Button launchButton;
    private Button settingsButton;
    private TextView statusText;
    private VersionManager versionManager;
    private MinecraftVersion selectedVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        checkPermissions();
        setupVersionList();
        setupClickListeners();
        
        versionManager = new VersionManager(this);
        loadVersions();
    }
    
    private void initViews() {
        versionsRecyclerView = findViewById(R.id.versionsRecyclerView);
        launchButton = findViewById(R.id.launchButton);
        settingsButton = findViewById(R.id.settingsButton);
        statusText = findViewById(R.id.statusText);
        
        statusText.setText("Minecraft Launcher Ready");
    }
    
    private void setupVersionList() {
        versions = new ArrayList<>();
        versionAdapter = new VersionAdapter(versions, this::onVersionSelected);
        versionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        versionsRecyclerView.setAdapter(versionAdapter);
    }
    
    private void setupClickListeners() {
        launchButton.setOnClickListener(v -> launchGame());
        settingsButton.setOnClickListener(v -> openSettings());
    }
    
    private void onVersionSelected(MinecraftVersion version) {
        selectedVersion = version;
        launchButton.setEnabled(true);
        statusText.setText("Selected: " + version.getId());
    }
    
    private void launchGame() {
        if (selectedVersion == null) {
            Toast.makeText(this, "Please select a version first", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("version", selectedVersion.getId());
        startActivity(intent);
    }
    
    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
    
    private void loadVersions() {
        statusText.setText("Loading versions...");
        versionManager.getVersions(new VersionManager.VersionCallback() {
            @Override
            public void onSuccess(List<MinecraftVersion> versionList) {
                runOnUiThread(() -> {
                    versions.clear();
                    versions.addAll(versionList);
                    versionAdapter.notifyDataSetChanged();
                    statusText.setText("Loaded " + versionList.size() + " versions");
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    statusText.setText("Error: " + error);
                    Toast.makeText(MainActivity.this, "Failed to load versions", Toast.LENGTH_LONG).show();
                });
            }
        });
    }
    
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Storage permission required for launcher", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}