package com.smtlabs.roomdatabasetesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.smtlabs.roomdatabasetesting.databinding.ActivityDashboardBinding;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private int id;
    private String FirstName, LastName;

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        UserDao userDao = db.userDao();

        try {
            binding.btnSaveData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = Integer.parseInt(binding.userId.getText().toString());
                    FirstName = binding.firstName.getText().toString();
                    LastName = binding.lastName.getText().toString();
                    binding.userId.setText("");
                    binding.firstName.setText("");
                    binding.lastName.setText("");

                    User user = new User(id, FirstName, LastName);
                    userDao.insertUser(user);
                    Toast.makeText(DashboardActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Occurred! Try Again...", Toast.LENGTH_SHORT).show();
            binding.userId.setText("");
            binding.firstName.setText("");
            binding.lastName.setText("");
        }

        binding.btnFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = userDao.getAll();
                binding.txtDisplay.setText("");

                for (int i = 0; i<users.size(); i++){
                    int disId = users.get(i).getUid();
                    String disFname = users.get(i).getFirstName();
                    String dislname = users.get(i).getLastName();

                    binding.txtDisplay.append("ID- "+ disId + "\nFirst Name: "+disFname+ "\nLast Name: "+dislname+"\n\n\n");
                }
            }
        });
    }
}