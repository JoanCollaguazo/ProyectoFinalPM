package com.example.proyectofinalpm;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectofinalpm.models.Persona;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends ArrayAdapter{

    Persona contact;
    private Context context;
    private List<Persona> contactArrayList;


    public ListAdapter(Context context, List<Persona> contactArrayList){
        super(context, R.layout.item);
        this.context=context;
        this.contactArrayList=contactArrayList;

    }

    @Override
    public int getCount() {
        return contactArrayList.size();
    }

    @Override
    public Persona getItem(int position) {
        return contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Este método permite obtener la vista a partir de una fila
    //la vista que representa la lista se apsa por parámetro al método
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        View view;
        final  ViewHolder viewHolder;
        if (convertView == null || convertView.getTag()==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            viewHolder.mContactName=(TextView) view.findViewById(R.id.adapterName);
            viewHolder.mImageContact= view.findViewById(R.id.adapterImage);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
            view=convertView;
        }

        viewHolder.mContactName.setText(contactArrayList.get(position).getNombre());
        Glide.with(context)
        //.getUrl() despues de positiion:
                .load(contactArrayList.get(position))
                .into(viewHolder.mImageContact);
        return  view;
    }


    static  class ViewHolder{
        protected TextView mContactName;
        protected ImageView mImageContact;

    }

}

