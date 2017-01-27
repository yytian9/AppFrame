package com.ytsky.appframe.modules.fresco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ytsky.appframe.R;

public class FrescoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdv_test);

//        Uri parse = Uri.parse("res://ez.png");
//        simpleDraweeView.setImageURI(parse);
//        simpleDraweeView.setImageResource(R.drawable.ez);


//        GenericDraweeHierarchyBuilder builder =
//                new GenericDraweeHierarchyBuilder(getResources());
//
//        GenericDraweeHierarchy build = builder.setPlaceholderImage(R.drawable.ez).build();
//        simpleDraweeView.setHierarchy(build);

        GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
        hierarchy.setPlaceholderImage(R.drawable.ez);


    }
}
