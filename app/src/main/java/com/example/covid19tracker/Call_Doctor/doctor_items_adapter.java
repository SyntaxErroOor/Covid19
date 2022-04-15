package com.example.covid19tracker.Call_Doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.covid19tracker.R;



import java.util.ArrayList;

public class doctor_items_adapter extends RecyclerView.Adapter<doctor_items_adapter.doctorViewholder> {

   private ArrayList<doctor_data> doctor_data_list;

    @NonNull
    @Override
    public doctorViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new doctorViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.docoter_item,parent,false));
    }



    public doctor_items_adapter(ArrayList<doctor_data> doctor_data_list)
    {
        this.doctor_data_list=doctor_data_list;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull doctorViewholder holder, int position) {

        holder.image_doctor.setImageResource(doctor_data_list.get(position).getImage_dactor());
        holder.name_doctor_1.setText(doctor_data_list.get(position).getName_doctor());
        holder.phone_doctor_2.setText(doctor_data_list.get(position).getPhone_doctor());

    }




    @Override
    public int getItemCount() {
        return doctor_data_list.size();
    }

    public class doctorViewholder extends RecyclerView.ViewHolder {
        private ImageView image_doctor ;
        private TextView name_doctor_1;
        private TextView   phone_doctor_2;

        public doctorViewholder(@NonNull View itemView) {
            super(itemView);
            image_doctor=itemView.findViewById(R.id.src_image_call_doctor);
            name_doctor_1=itemView.findViewById(R.id.doctor_name);
            phone_doctor_2=itemView.findViewById(R.id.phone_doctor);

        }
    }
}