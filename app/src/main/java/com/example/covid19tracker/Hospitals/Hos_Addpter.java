package com.example.covid19tracker.Hospitals;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid19tracker.Call_Doctor.doctor_data;
import com.example.covid19tracker.Call_Doctor.doctor_items_adapter;
import com.example.covid19tracker.R;
import java.util.ArrayList;
import java.util.List;


public class Hos_Addpter extends RecyclerView.Adapter<Hos_Addpter.HosViewHolder> {

    private ArrayList<Hospitals_Data> Hospital_data_list;
  //  private Hos_Addpter.OnItemClickListener mLitener;



//    public interface OnItemClickListener {
//        void onItemClick(int poisition);
//
//    }

//    public   void setOnItemClickListener(Hos_Addpter.OnItemClickListener listener)
//    {
//        mLitener=listener;
//    }



    @NonNull
    @Override
    public HosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitals_items,parent,false));
    }


    public Hos_Addpter(ArrayList<Hospitals_Data> Hospital_data_list)
    {
        this.Hospital_data_list=Hospital_data_list;
        notifyDataSetChanged();

    }





    @Override
    public void onBindViewHolder(@NonNull HosViewHolder holder, int position) {

        //holder.image_doctor.setImageBitmap(Hospital_data_list.get(position).getImage_hospital());
        Glide.with(holder.image_hospital)
                .load(Hospital_data_list.get(position).getImage_url())
                .into(holder.image_hospital);
        holder.name_hospital.setText(Hospital_data_list.get(position).getName_hospital());
        holder.address_hospital.setText(Hospital_data_list.get(position).getHospital_address());



    }



    @Override
    public int getItemCount() {
        return Hospital_data_list.size();
    }





    public class HosViewHolder  extends RecyclerView.ViewHolder{

        private ImageView image_hospital ;
        private TextView name_hospital;
        private TextView   address_hospital;

        public HosViewHolder(@NonNull View itemView) {
            super(itemView);

            image_hospital=itemView.findViewById(R.id.src_image_hospital);
            name_hospital=itemView.findViewById(R.id.hospital_name);
            address_hospital=itemView.findViewById(R.id.address_hospital);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(Litener!=null)
//                    {
//                        int poisiton=getAdapterPosition();
//                        if(poisiton!=RecyclerView.NO_POSITION)
//                        {
//                            Litener.onItemClick(poisiton);
//                        }
//
//                    }
//
//                }
//            });



        }
    }
}
