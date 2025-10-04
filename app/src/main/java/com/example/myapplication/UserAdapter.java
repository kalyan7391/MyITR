package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<String> userList;
    private List<String> userListFull; // A copy for filtering
    private OnUserActionsListener listener;

    // Interface to handle clicks
    public interface OnUserActionsListener {
        void onEditUser(String username);
        void onDeleteUser(String username);
    }

    public UserAdapter(List<String> userList, OnUserActionsListener listener) {
        this.userList = userList;
        this.userListFull = new ArrayList<>(userList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String username = userList.get(position);
        holder.tvUserName.setText(username);
        // Note: Your DB only has username. We'll set placeholder text for other details.
        holder.tvUserDetails.setText("Username: " + username);

        holder.ivEdit.setOnClickListener(v -> listener.onEditUser(username));
        holder.ivDelete.setOnClickListener(v -> listener.onDeleteUser(username));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Method to filter the list based on search query
    public void filter(String text) {
        userList.clear();
        if (text.isEmpty()) {
            userList.addAll(userListFull);
        } else {
            text = text.toLowerCase();
            for (String item : userListFull) {
                if (item.toLowerCase().contains(text)) {
                    userList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserDetails;
        ImageView ivEdit, ivDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserDetails = itemView.findViewById(R.id.tv_user_details);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
}