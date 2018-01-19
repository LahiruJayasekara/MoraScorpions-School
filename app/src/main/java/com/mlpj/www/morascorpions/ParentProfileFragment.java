package com.mlpj.www.morascorpions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentProfileFragment extends Fragment {

    private UserLocalStore userLocalStore;
    private User currentUser;
    private CircleImageView imgProfilePic;
    private TextView tvProfileParentName, tvProfileParentTp, tvProfileParentEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_profile, container, false);

        userLocalStore = new UserLocalStore(getContext());
        currentUser = userLocalStore.getUserDetails();

        imgProfilePic = view.findViewById(R.id.imgProfileParentPic);
        tvProfileParentName = view.findViewById(R.id.tvProfileParentName);
        tvProfileParentTp = view.findViewById(R.id.tvProfileParentTp);
        tvProfileParentEmail = view.findViewById(R.id.tvProfileParentEmail);

        if(currentUser.getPicUrl() != null){
            Picasso.with(getContext()).load(currentUser.getPicUrl()).into(imgProfilePic);
        }

        tvProfileParentName.setText(currentUser.getName());
        tvProfileParentTp.setText(currentUser.getTpNumber());
        tvProfileParentEmail.setText(currentUser.getEmail());

        return view;
    }

}
