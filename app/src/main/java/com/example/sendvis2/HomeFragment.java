package com.example.sendvis2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.time.LocalTime;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        CardView c0 = view.findViewById(R.id.card_home_0);
        ((TextView)c0.findViewById(R.id.tv_ora_limited_time)).setText(LocalTime.now().plusHours(2).toString().substring(0, 5));

        CardView c1 = view.findViewById(R.id.card_home_1);
        CardView c2 = view.findViewById(R.id.card_home_2);
        CardView c3 = view.findViewById(R.id.card_home_3);
        CardView c4 = view.findViewById(R.id.card_home_4);

        ((TextView)c1.findViewById(R.id.tv_name_leaderboard)).setText("Marcel Popescu");
        ((TextView)c2.findViewById(R.id.tv_name_leaderboard)).setText("Ion Daniel");
        ((TextView)c3.findViewById(R.id.tv_name_leaderboard)).setText("Lucian Liviu");
        ((TextView)c4.findViewById(R.id.tv_name_leaderboard)).setText("Dragos Badea");

        ((TextView)c1.findViewById(R.id.tv_place_leaderboard)).setText("1 ");
        ((TextView)c2.findViewById(R.id.tv_place_leaderboard)).setText("2 ");
        ((TextView)c3.findViewById(R.id.tv_place_leaderboard)).setText("3 ");
        ((TextView)c4.findViewById(R.id.tv_place_leaderboard)).setText("6 ");

        ((TextView)c1.findViewById(R.id.tv_points_leaderboard)).setText("1000");
        ((TextView)c2.findViewById(R.id.tv_points_leaderboard)).setText("200");
        ((TextView)c3.findViewById(R.id.tv_points_leaderboard)).setText("30");
        ((TextView)c4.findViewById(R.id.tv_points_leaderboard)).setText("4");

        Button toPromos = view.findViewById(R.id.btn_see_all_offers);
        Button toLeaderboard = view.findViewById(R.id.btn_see_all_leaderboard);
        Button payVendor = view.findViewById(R.id.btn_pay_vendor);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();
        DatabaseReference paymentsReference = mDatabase.child("Balance");
        TextView balance = view.findViewById(R.id.tv_wallet_balance);

        paymentsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balance.setText(snapshot.getValue(Integer.class).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PromosActivity.class);
                startActivity(intent);
            }
        });

        toLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        payVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanQRActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
