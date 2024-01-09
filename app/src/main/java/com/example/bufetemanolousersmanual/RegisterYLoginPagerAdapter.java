package com.example.bufetemanolousersmanual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Locale;

public class RegisterYLoginPagerAdapter extends PagerAdapter {

    private Context context;
    private int[] images = {R.drawable.register, R.drawable.login};
    private TextView[] textViews = new TextView[2];

    public RegisterYLoginPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.login_y_register_adapter, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        textViews[0] = itemView.findViewById(R.id.loginDescription);
        textViews[1] = itemView.findViewById(R.id.registerDescription);

        obtenerDatosFirebase(position);


        container.addView(itemView);

        return itemView;
    }

    private void obtenerDatosFirebase(final int position) {
        FirebaseUtils.readData("manual", "paginaLoginYRegister",
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Locale currentLocale = context.getResources().getConfiguration().locale;
                            String currentLanguage = currentLocale.getLanguage();

                            String textKey = (position == 0) ? "spanish-text1" : "spanish-text2";
                            String text = documentSnapshot.getString(currentLanguage + "-" + textKey);
                            textViews[position].setText(text);
                        }
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}