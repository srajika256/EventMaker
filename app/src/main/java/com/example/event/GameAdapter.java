package com.example.event;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyClass> {
    ArrayList<GetterSetter>a1;
    Context context;


    public GameAdapter(Context context, ArrayList<GetterSetter> a1) {
        this.context=context;
        this.a1=a1;
    }


    @NonNull
    @Override
    public GameAdapter.MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_view, parent, false);
        return new MyClass(v);
    }


    public void CreateAlertDialog(String gname,  final int gid) {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        LinearLayout ll_alert_layout = new LinearLayout(context);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ed_input = new EditText(context);
        final EditText ed_input1=new EditText(context);
        final EditText ed_input2=new EditText(context);
        ed_input.setHint("Enter Your Name");
        ed_input1.setHint("Enter Your Email");
        ed_input2.setHint("Enter Your team name");
        ll_alert_layout.addView(ed_input);
        ll_alert_layout.addView(ed_input1);
        ll_alert_layout.addView(ed_input2);

        alertbox.setTitle("Register In "+ gname);

        //setting linear layout to alert dialog

        alertbox.setView(ll_alert_layout);

        alertbox.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {

                        // will automatically dismiss the dialog and will do nothing

                    }
                });


        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        String name = ed_input.getText().toString();
                        String email = ed_input1.getText().toString();
                        String tname = ed_input2.getText().toString();
                        User_table db1=new User_table(context);
                        team_table db2 = new team_table(context);
                        db1.adddata(name, email);//aak error
                        db2.adddata(tname);
                        int uid = db1.query(email);
                        int tid = db2.query(tname);
                        user_team db3 = new user_team(context);
                        game_team db4 = new game_team(context);
                        db3.adddata(uid, tid);
                        db4.adddata(gid, tid);
                        //put enteries in database

                        // do your action with input string

                    }
                });
        alertbox.show();
    }
    @Override
    public void onBindViewHolder(@NonNull GameAdapter.MyClass holder, final int position) {
        final String name;
        final GetterSetter g1=a1.get(position);
        holder.gid.setText(g1.getGid());
        name=g1.getGname();
        holder.gname.setText(g1.getGname());
        holder.gsize.setText(g1.getGsize());
        Log.e("GETTER",g1.getGid()+g1.getGname()+g1.getGsize());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
                CreateAlertDialog(g1.getGname(), Integer.valueOf(g1.getGid()));
            }
        });


    }


    @Override
    public int getItemCount() {
        return a1.size();
    }

    public class MyClass extends RecyclerView.ViewHolder {
        TextView gid, gname, gsize;
        public MyClass(View ItemView) {
            super(ItemView);
            gid = ItemView.findViewById(R.id.gid);
            gname = ItemView.findViewById(R.id.gname);
            gsize = ItemView.findViewById(R.id.gsize);


        }
    }
}



