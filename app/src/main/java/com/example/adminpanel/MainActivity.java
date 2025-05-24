package com.example.adminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    private TextView textUserCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textUserCount = findViewById(R.id.textUserCount);
        Button btnUser = findViewById(R.id.btn_user);

        btnUser.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AdminUsersActivity.class));
        });

        // Load the total user count from Firebase
        loadTotalUsersCount();
    }

    private void loadTotalUsersCount() {
        FirebaseDatabase.getInstance().getReference("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long userCount = snapshot.getChildrenCount(); // Count total users
                        textUserCount.setText("Total Users: " + userCount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        textUserCount.setText("Failed to load user count");
                    }
                });
    }
}
