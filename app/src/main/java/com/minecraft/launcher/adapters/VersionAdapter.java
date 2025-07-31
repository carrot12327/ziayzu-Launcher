package com.minecraft.launcher.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minecraft.launcher.R;
import com.minecraft.launcher.models.MinecraftVersion;
import java.util.List;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.VersionViewHolder> {
    private List<MinecraftVersion> versions;
    private OnVersionSelectedListener listener;
    private int selectedPosition = -1;
    
    public interface OnVersionSelectedListener {
        void onVersionSelected(MinecraftVersion version);
    }
    
    public VersionAdapter(List<MinecraftVersion> versions, OnVersionSelectedListener listener) {
        this.versions = versions;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public VersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_version, parent, false);
        return new VersionViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull VersionViewHolder holder, int position) {
        MinecraftVersion version = versions.get(position);
        holder.bind(version, position == selectedPosition);
        
        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = position;
            
            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedPosition);
            
            if (listener != null) {
                listener.onVersionSelected(version);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return versions.size();
    }
    
    static class VersionViewHolder extends RecyclerView.ViewHolder {
        private TextView versionName;
        private TextView versionType;
        
        public VersionViewHolder(@NonNull View itemView) {
            super(itemView);
            versionName = itemView.findViewById(R.id.versionName);
            versionType = itemView.findViewById(R.id.versionType);
        }
        
        public void bind(MinecraftVersion version, boolean isSelected) {
            versionName.setText(version.getId());
            versionType.setText(version.getType().toUpperCase());
            
            // Highlight selected item
            itemView.setSelected(isSelected);
            itemView.setBackgroundResource(isSelected ? 
                R.drawable.selected_version_background : 
                R.drawable.version_background);
        }
    }
}