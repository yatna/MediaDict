package test.com.carwash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.Collections;
import java.util.List;

/**
 * Created by DELL on 5/25/2015.
 */
public class VivzAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {


Intent intent;
   private LayoutInflater inflater;


    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;

String name,phone;
    List<Information> data= Collections.emptyList();
   private Context context;
    private ClickListener clickListener;
    private String MY_PREFS_NAME="DEFAULT";
   public VivzAdapter(Context context,List<Information> data,String name,String phone){
      this.context=context;
       inflater= LayoutInflater.from(context);
       this.data=data;
this.name=name;
       this.phone=phone;

   }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType==TYPE_HEADER){

            View view=inflater.inflate(R.layout.drawer_header, parent,false);
//View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header,parent,false);
            //String n="";
//Intent intent=((Register)context).getIntent();
//intent.putExtra("Name",n.getText().toString());

            //drawerHeader.onCreate(view);

            HeaderHolder holder=new HeaderHolder(view);
            return holder;
        }
        else{
            View view=inflater.inflate(R.layout.custom_view, parent,false);
            ItemHolder holder=new ItemHolder(view);
            return holder;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderHolder ){
            HeaderHolder headerHolder=(HeaderHolder)holder;
            headerHolder.etName.setText(name);
            headerHolder.etPhone.setText(phone);

        }
        else{
            ItemHolder itemHolder= (ItemHolder) holder;
            Information current=data.get(position-1);
            itemHolder.title.setText(current.title);
            itemHolder.icon.setImageResource(current.iconId);
        }
    }
public void setClickListener(ClickListener clickListener){
    this.clickListener=clickListener;
}
    @Override
    public int getItemCount() {
        return data.size()+1;
    }



    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       TextView title;
        ImageView icon;


        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=(TextView)itemView.findViewById(R.id.tv1);
            icon=(ImageView)itemView.findViewById(R.id.iv1);

        }

        @Override
        public void onClick(View v) {
            //context.startActivity(new Intent(context,NearSirviceStations.class));
       if(clickListener!=null)
       {
           clickListener.itemClicked(v,getPosition()-1);
       }

        }
    }
    public interface ClickListener{
        public void itemClicked(View view, int position);
    }

    class HeaderHolder extends RecyclerView.ViewHolder  {
TextView etName,etPhone;


        public HeaderHolder(View itemView) {
            super(itemView);
etName= (TextView) itemView.findViewById(R.id.etName);
            etPhone= (TextView) itemView.findViewById(R.id.etPhone);


        }



        }
    }





