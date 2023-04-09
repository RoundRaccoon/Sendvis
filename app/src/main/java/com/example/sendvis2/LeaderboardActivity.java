package com.example.sendvis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView rankingView;
    private ArrayList<Rank> ranks;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        rankingView = findViewById(R.id.leaderboard);
        rankingView.setLayoutManager(new LinearLayoutManager(this));
        rankingView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();

        DatabaseReference ranksReference = mDatabase.child("leaderboard");
        ranks = new ArrayList<>();

        ranksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String place = snapshot.child("place").getValue(String.class);
                    String name = " " + snapshot.child("name").getValue(String.class);
                    String points = snapshot.child("points").getValue(String.class);

                    if (place.equals("1"))
                        name = name + "\uD83E\uDD47";
                    else if (place.equals("2"))
                        name = name + "\uD83E\uDD48";
                    else if (place.equals("3"))
                        name = name + "\uD83E\uDD49";
                    else if (place.equals("4"))
                        name = name + "\uD83C\uDFC3";

                    ranks.add(new Rank(place, name, points));
                }

                RankAdapter adapter = new RankAdapter(getApplicationContext(), ranks);
                rankingView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    Context context;
    ArrayList<Rank> ranks;

    public RankAdapter(Context context, ArrayList<Rank> ranks) {
        this.context = context;
        this.ranks = ranks;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_leaderboard, parent, false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        Rank rank = ranks.get(position);

        holder.place.setText(rank.getPlace());
        holder.name.setText(rank.getName());
        holder.points.setText(rank.getPoints());
    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }

    public static class RankViewHolder extends RecyclerView.ViewHolder {

        TextView place, name, points;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.tv_place_leaderboard);
            name = itemView.findViewById(R.id.tv_name_leaderboard);
            points = itemView.findViewById(R.id.tv_points_leaderboard);
        }
    }

}

class Rank {

    String place, name, points;

    public Rank(String place, String name, String points) {
        this.place = place;
        this.name = name;
        this.points = points;
    }

    public String getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return points;
    }
}