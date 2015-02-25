package com.example.coen485prototype1;

        import android.os.Bundle;
        import android.view.View;
        import android.app.Activity;
        import android.app.Fragment;
        import android.app.FragmentManager;
        import android.app.FragmentTransaction;
        import android.support.v4.app.FragmentActivity;


public class Events extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
    }
    public void selectFrag(View view) {
        Fragment fr;
        if(view == findViewById(R.id.button2)) {
            fr = new FragmentTwo();
        }else {
            fr = new FragmentOne();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();
    }
}
