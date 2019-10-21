package com.comp90018.drpet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * @author xinli
 * @date 2019-10-19
 */
public class PetList extends ArrayAdapter<Pet> {
    private Activity context;
    private List<Pet> petList;

    public PetList(Activity context, List<Pet> petList) {
        super(context, R.layout.petlist, petList);
        this.context = context;
        this.petList = petList;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.petlist, null,true);

        TextView textViewDoctorName = (TextView) listViewItem.findViewById(R.id.PetList);

        Pet pet = petList.get(position);

        textViewDoctorName.setText(pet.getPetName()+" "+pet.getPetAge()+" "+pet.getBreed()+ " "+ pet.getCategory()+ " "+ pet.getComment());

        return listViewItem;

    }

}
