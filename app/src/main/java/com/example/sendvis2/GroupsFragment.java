package com.example.sendvis2;

import android.content.Context;
import android.content.Intent;
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

public class GroupsFragment extends Fragment {

    private TextView overallOweText, overallOweValueText;

    private RecyclerView groupsView;
    private ArrayList<Group> groups;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups,container,false);

        overallOweText = view.findViewById(R.id.overall_owe_text);
        overallOweValueText = view.findViewById(R.id.overall_owe_value);

        groupsView = view.findViewById(R.id.groupsList);
        groupsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupsView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();

        DatabaseReference groupsReference = mDatabase.child("groups");
        groups = new ArrayList<>();

        groupsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double balance = 0.0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);

                    String owedSum = snapshot.child("owedSum").getValue(String.class);
                    balance += Double.parseDouble(owedSum);

                    ArrayList<String> members = new ArrayList<>();
                    for (DataSnapshot memberReference: snapshot.child("members").getChildren()) {
                        String memberName = memberReference.getKey();
                        members.add(memberName);
                    }

                    Group group = new Group(name, members, owedSum);
                    groups.add(group);
                }

                StringBuilder builder = new StringBuilder();
                builder.append(balance);
                String sumToOwe = builder.toString();
                if (sumToOwe.charAt(0) == '-') {
                    overallOweText.setText("Overall, you owe ");
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

        GroupAdapter adapter = new GroupAdapter(getContext(), groups);
        groupsView.setAdapter(adapter);

        return view;
    }
}

class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    Context context;
    ArrayList<Group> groupArrayList;

    public GroupAdapter(Context context, ArrayList<Group> groupArrayList) {
        this.context = context;
        this.groupArrayList = groupArrayList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_row, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groupArrayList.get(position);
        holder.name.setText(group.getName());

        ArrayList<String> members = group.getMembers();
        StringBuilder membersText = new StringBuilder();

        membersText.append(members.size());
        membersText.append(" members: ");

        membersText.append(members.get(0));
        membersText.append(", ");
        membersText.append(members.get(1));

        if (members.size() > 3) {
            membersText.append(" and others");
        } else {
            membersText.append(" and ");
            membersText.append(members.get(2));
        }

        holder.numberOfMembers.setText(membersText);

        String sumToOwe = group.getOwedSum();
        if (sumToOwe.charAt(0) == '-') {
            holder.owedSum.setText("You owe RON" + sumToOwe.substring(1));
            holder.owedSum.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        } else {
            holder.owedSum.setText("You are owed RON" + sumToOwe);
            holder.owedSum.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return groupArrayList.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView numberOfMembers;
        TextView owedSum;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.group_name);
            numberOfMembers = itemView.findViewById(R.id.num_members);
            owedSum = itemView.findViewById(R.id.sum_to_owe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TransactionsActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}

class Group {

    private String name;
    private ArrayList<String> members;
    private String owedSum;

    public Group(String name, ArrayList<String> members, String owedSum) {
        this.name = name;
        this.members = members;
        this.owedSum = owedSum;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public String getOwedSum() {
        return owedSum;
    }
}