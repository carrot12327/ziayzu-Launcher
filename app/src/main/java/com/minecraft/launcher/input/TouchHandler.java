package com.minecraft.launcher.input;

import android.view.MotionEvent;
import com.minecraft.launcher.game.GameEngine;

public class TouchHandler {
    private GameEngine gameEngine;
    private float lastX, lastY;
    private boolean isDragging = false;
    
    public TouchHandler(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
    
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                isDragging = true;
                gameEngine.onTouch(x, y, MotionEvent.ACTION_DOWN);
                return true;
                
            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    float deltaX = x - lastX;
                    float deltaY = y - lastY;
                    
                    // Handle camera rotation based on touch movement
                    handleCameraRotation(deltaX, deltaY);
                    
                    lastX = x;
                    lastY = y;
                    gameEngine.onTouch(x, y, MotionEvent.ACTION_MOVE);
                }
                return true;
                
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                gameEngine.onTouch(x, y, MotionEvent.ACTION_UP);
                return true;
        }
        
        return false;
    }
    
    private void handleCameraRotation(float deltaX, float deltaY) {
        // Convert touch movement to camera rotation
        float sensitivity = 0.1f;
        float yaw = deltaX * sensitivity;
        float pitch = deltaY * sensitivity;
        
        // Apply rotation to game camera
        // This would interface with the actual camera system
    }
}