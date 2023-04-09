package com.example.sendvis2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LedgerFragment extends Fragment {

    private RecyclerView ledgerView;
    private ArrayList<Payment> payments;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ledger,container,false);

        ledgerView = view.findViewById(R.id.ledger_view);
        ledgerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ledgerView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();

        DatabaseReference paymentsReference = mDatabase.child("Payments");
        payments = new ArrayList<>();

        paymentsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String target = snapshot.child("target").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    double amount = snapshot.child("amount").getValue(Long.class);
                    boolean sent = snapshot.child("sent").getValue(Boolean.class);
                    boolean vendor = snapshot.child("vendor").getValue(Boolean.class);
                    payments.add(new Payment(target, date, amount, sent, vendor));
                }

                PaymentAdapter adapter = new PaymentAdapter(getContext(), payments);
                ledgerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}

class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    Context context;
    ArrayList<Payment> payments;

    public PaymentAdapter(Context context, ArrayList<Payment> payments) {
        this.context = context;
        this.payments = payments;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_ledger, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = payments.get(position);

        holder.target.setText(payment.getTarget());

        if (payment.vendor) {
            holder.paid.setText("paid ");
            holder.amount.setText("RON" + payment.getAmount());

            holder.paid.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
            holder.amount.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        } else if (payment.sent) {
            holder.paid.setText("sent ");
            holder.amount.setText("RON" + payment.getAmount());

            holder.paid.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
            holder.amount.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        } else  {
            holder.paid.setText("received ");
            StringBuilder builder = new StringBuilder();
            builder.append(payment.getAmount());
            String s = builder.toString();
//            holder.amount.setText("RON" + s.substring(1));
            holder.amount.setText("RON" + s);


            holder.paid.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
            holder.amount.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        }

        holder.date.setText(payment.getDate());
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView target;
        TextView paid;
        TextView amount;
        TextView date;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            target = itemView.findViewById(R.id.card_ledger_target);
            paid = itemView.findViewById(R.id.card_ledger_sent);
            amount = itemView.findViewById(R.id.card_ledger_amount);
            date = itemView.findViewById(R.id.card_ledger_date);
        }
    }

}

class Payment {
    String target, date;
    double amount;
    boolean sent, vendor;

    public Payment(String target, String date, double amount, boolean sent, boolean vendor) {
        this.target = target;
        this.date = date;
        this.amount = amount;
        this.sent = sent;
        this.vendor = vendor;
    }

    public String getTarget() {
        return target;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isSent() {
        return sent;
    }

    public boolean isVendor() {
        return vendor;
    }
}