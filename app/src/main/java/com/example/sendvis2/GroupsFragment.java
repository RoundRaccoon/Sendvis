package com.example.sendvis2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupsFragment extends Fragment {

    private TextView overallOweText, overallOweValueText;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups,container,false);

        overallOweText = view.findViewById(R.id.overall_owe_text);
        overallOweValueText = view.findViewById(R.id.overall_owe_value);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();
        mDatabase.child("sumToOwe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sumToOwe = snapshot.getValue(String.class);
                if (sumToOwe.charAt(0) == '-') {
                    overallOweValueText.setText("RON" + sumToOwe.substring(1));
                    overallOweValueText.setTextColor(getResources().getColor(R.color.red));
                } else {
                    overallOweText.setText("Overall, you are owed ");
                    overallOweValueText.setText("RON" + sumToOwe);
                    overallOweValueText.setTextColor(getResources().getColor(R.color.green));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
