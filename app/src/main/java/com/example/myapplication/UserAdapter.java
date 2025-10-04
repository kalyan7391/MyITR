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

// ✨ Change the adapter to work with a List of User objects
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private List<User> userListFull;
    private OnUserActionsListener listener;

    public interface OnUserActionsListener {
        void onEditUser(User user);
        void onDeleteUser(User user);
    }

    public UserAdapter(List<User> userList, OnUserActionsListener listener) {
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
        User currentUser = userList.get(position);

        // ✨ Set all the text fields from the User object
        holder.tvUserName.setText(currentUser.getName());
        String details = "Employee ID: " + currentUser.getEmployeeId() + "\n" +
                "Phone: " + currentUser.getPhone() + " | DOB: " + currentUser.getDob();
        holder.tvUserDetails.setText(details);

        holder.ivEdit.setOnClickListener(v -> listener.onEditUser(currentUser));
        holder.ivDelete.setOnClickListener(v -> listener.onDeleteUser(currentUser));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void filter(String text) {
        userList.clear();
        if (text.isEmpty()) {
            userList.addAll(userListFull);
        } else {
            text = text.toLowerCase();
            for (User item : userListFull) {
                // Filter by name or ID
                if (item.getName().toLowerCase().contains(text) || item.getEmployeeId().contains(text)) {
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