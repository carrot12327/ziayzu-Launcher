package com.minecraft.launcher;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.minecraft.launcher.game.MinecraftRenderer;
import com.minecraft.launcher.game.GameEngine;
import com.minecraft.launcher.input.TouchHandler;

public class GameActivity extends Activity {
    private GLSurfaceView glSurfaceView;
    private MinecraftRenderer renderer;
    private GameEngine gameEngine;
    private TouchHandler touchHandler;
    private String versionId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get version from intent
        versionId = getIntent().getStringExtra("version");
        if (versionId == null) {
            Toast.makeText(this, "No version specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        // Set fullscreen and landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // Hide navigation bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        
        setupOpenGL();
        initializeGame();
    }
    
    private void setupOpenGL() {
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        
        renderer = new MinecraftRenderer(this);
        glSurfaceView.setRenderer(renderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        
        setContentView(glSurfaceView);
    }
    
    private void initializeGame() {
        gameEngine = new GameEngine(this, versionId);
        touchHandler = new TouchHandler(gameEngine);
        
        // Start game loading
        gameEngine.startGame(new GameEngine.GameCallback() {
            @Override
            public void onGameLoaded() {
                runOnUiThread(() -> {
                    Toast.makeText(GameActivity.this, "Minecraft loaded successfully", Toast.LENGTH_SHORT).show();
                });
            }
            
            @Override
            public void onGameError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(GameActivity.this, "Game error: " + error, Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        });
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchHandler != null) {
            return touchHandler.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Show pause menu or exit confirmation
            gameEngine.pauseGame();
            finish();
            return true;
        }
        
        if (gameEngine != null) {
            gameEngine.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (gameEngine != null) {
            gameEngine.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
        if (gameEngine != null) {
            gameEngine.resumeGame();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
        if (gameEngine != null) {
            gameEngine.pauseGame();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameEngine != null) {
            gameEngine.stopGame();
        }
    }
}