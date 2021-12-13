package com.kazimasum.arshadroomdb;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    List<User> users;

    public myadapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myadapter.myviewholder holder, @SuppressLint("RecyclerView") int position) {
          holder.recid.setText(String.valueOf(users.get(position).getUid()));
          holder.recfname.setText(users.get(position).getFirstName());
          holder.reclname.setText(users.get(position).getLastName());
          holder.delbtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  AppDatabase db = Room.databaseBuilder(holder.recid.getContext(),
                          AppDatabase.class, "room_db").allowMainThreadQueries().build();
                  UserDao userDao = db.userDao();
                  // this is to delete the record from room database
                  userDao.deleteById(users.get(position).getUid());
                  // this is to delete the record from Array List which is the source of recview data
                  users.remove(position);

                  //update the fresh list of ArrayList data to recview
                  notifyDataSetChanged();
              }
          });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
       {
           TextView recid,recfname, reclname;
           ImageButton delbtn;
           public myviewholder(@NonNull @NotNull View itemView) {
               super(itemView);

               recid=itemView.findViewById(R.id.recid);
               recfname=itemView.findViewById(R.id.recfname);
               reclname=itemView.findViewById(R.id.reclname);
               delbtn=itemView.findViewById(R.id.delbtn);
           }
       }
}
