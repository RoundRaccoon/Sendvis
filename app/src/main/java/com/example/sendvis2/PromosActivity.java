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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PromosActivity extends AppCompatActivity {

    private RecyclerView promosView;
    private DatabaseReference mDatabase;
    private ArrayList<Promo> promos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promos);

        promosView = findViewById(R.id.megabangerview);
        promosView.setLayoutManager(new LinearLayoutManager(this));
        promosView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();
        DatabaseReference promosReference = mDatabase.child("promos");
        promos = new ArrayList<>();

        promosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String vendor = snapshot.child("vendor").getValue(String.class);
                    String title = snapshot.child("title").getValue(String.class);

                    if (snapshot.hasChild("complete")) {
                        String current = snapshot.child("current").getValue(String.class);
                        String complete = snapshot.child("complete").getValue(String.class);
                        String number = snapshot.child("number").getValue(String.class);
                        String quantifier = snapshot.child("quantifier").getValue(String.class);

                        Promo promo = new Promo(vendor, title, current, complete, number, quantifier);
                        promos.add(promo);
                    } else {
                        String desc = snapshot.child("desc").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String hour = snapshot.child("hour").getValue(String.class);

                        Promo promo = new Promo(vendor, title, desc, date, hour);
                        promos.add(promo);
                    }

                }
                PromoAdapter adapter = new PromoAdapter(getApplicationContext(), promos);
                promosView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

class PromoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Promo> promos;

    public PromoAdapter(Context context, ArrayList<Promo> promos) {
        this.context = context;
        this.promos = promos;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return promos.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: return new OngoingOfferViewHolder(LayoutInflater.from(context).inflate(R.layout.card_oferta_ongoing, parent, false));
            case 1: return new LimitedOfferViewHolder(LayoutInflater.from(context).inflate(R.layout.card_oferta_limited_time, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int ivt = holder.getItemViewType();
        if (ivt == 0) {
            OngoingOfferViewHolder viewHolder = (OngoingOfferViewHolder) holder;
            Promo promo = promos.get(position);
            viewHolder.vendor.setText(promo.getVendor());
            viewHolder.title.setText(promo.getTitle());
            viewHolder.number.setText(promo.getCurrent());
            viewHolder.quantifier.setText(" " + promo.getQuantifier());
            viewHolder.current.setText(promo.getCurrent());
            viewHolder.complete.setText(promo.getComplete());

            int current = Integer.parseInt(promo.getCurrent());
            int complete = Integer.parseInt(promo.getComplete());
            viewHolder.progressBar.setMax(100);
            viewHolder.progressBar.setProgress(100 * current / complete, true);

        } else if (ivt == 1) {
            LimitedOfferViewHolder viewHolder = (LimitedOfferViewHolder) holder;
            Promo promo = promos.get(position);
            viewHolder.vendor.setText(promo.getVendor());
            viewHolder.title.setText(promo.getTitle());
            viewHolder.desc.setText(promo.getDesc());
            viewHolder.date.setText(promo.getDate());
            viewHolder.hour.setText(promo.getHour());
        }

    }

    public static class LimitedOfferViewHolder extends RecyclerView.ViewHolder {
        TextView vendor, title, desc, date, hour;


        public LimitedOfferViewHolder(@NonNull View itemView) {
            super(itemView);
            vendor = itemView.findViewById(R.id.tv_vendor_limited_time);
            title = itemView.findViewById(R.id.tv_title_limited_time);
            desc = itemView.findViewById(R.id.tv_description_limited_time);
            date = itemView.findViewById(R.id.tv_data_limited_time);
            hour = itemView.findViewById(R.id.tv_ora_limited_time);
        }
    }

    public static class OngoingOfferViewHolder extends RecyclerView.ViewHolder {
        TextView vendor, title, number, quantifier, current, complete;
        ProgressBar progressBar;


        public OngoingOfferViewHolder(@NonNull View itemView) {
            super(itemView);
            vendor = itemView.findViewById(R.id.tv_vendor_ongoing);
            title = itemView.findViewById(R.id.tv_title_ongoing);
            number = itemView.findViewById(R.id.tv_number_ongoing);
            quantifier = itemView.findViewById(R.id.tv_quantifier_ongoing);
            progressBar = itemView.findViewById(R.id.progress_bar);
            current = itemView.findViewById(R.id.tv_number_ongoing_2);
            complete = itemView.findViewById(R.id.tv_maxNumber_ongoing);
        }
    }

}

class Promo {

    String vendor;
    String title;
    String desc;

    String current, complete;
    String date, hour;
    String number, quantifier;

    public Promo(String vendor, String title, String current, String complete, String number, String quantifier) {
        this.vendor = vendor;
        this.title = title;
        this.current = current;
        this.complete = complete;
        this.number = number;
        this.quantifier = quantifier;
    }

    public Promo(String vendor, String title, String desc, String date, String hour) {
        this.vendor = vendor;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.hour = hour;
    }

    public String getVendor() {
        return vendor;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getCurrent() {
        return current;
    }

    public String getComplete() {
        return complete;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getQuantifier() {
        return quantifier;
    }

    public String getNumber() {
        return number;
    }
}