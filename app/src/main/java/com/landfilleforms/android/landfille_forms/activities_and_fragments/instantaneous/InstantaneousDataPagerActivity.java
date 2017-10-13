package com.landfilleforms.android.landfille_forms.activities_and_fragments.instantaneous;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.landfilleforms.android.landfille_forms.R;
import com.landfilleforms.android.landfille_forms.database.dao.InstantaneousDao;
import com.landfilleforms.android.landfille_forms.model.InstantaneousData;

import java.util.List;
import java.util.UUID;

/**
 * Created by Work on 11/3/2016.
 */

public class InstantaneousDataPagerActivity extends AppCompatActivity {
    private static final String EXTRA_INSTANTANEOUS_DATA_ID = "com.landfilleforms.android.landfille_forms.instantaneous_data_id";

    private ViewPager mViewPager;
    private List<InstantaneousData> mInstantaneousDataList;

    public static Intent newIntent(Context packageContext, UUID instantaneousDataId) {
        Intent intent = new Intent (packageContext, InstantaneousDataPagerActivity.class);
        intent.putExtra(EXTRA_INSTANTANEOUS_DATA_ID, instantaneousDataId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantaneous_data_pager);

        UUID instantaneousDataId = (UUID) getIntent().getSerializableExtra(EXTRA_INSTANTANEOUS_DATA_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_instantaneous_data_pager_view_pager);

        mInstantaneousDataList = InstantaneousDao.get(this).getInstantaneousDatas();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                InstantaneousData instantaneousData = mInstantaneousDataList.get(position);
                return InstantaneousDataFragment.newInstance(instantaneousData.getId());
            }

            @Override
            public int getCount() {
                return mInstantaneousDataList.size();
            }
        });

        for(int i = 0; i < mInstantaneousDataList.size(); i++) {
            if (mInstantaneousDataList.get(i).getId().equals(instantaneousDataId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    //TODO: Implement so that it asks if you wanna discard changes when back is pressed. Issues: Data pager might mess up how data is saved.
    @Override
    public void onBackPressed() {
        //this generates the dialog when user backs out
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        InstantaneousDataFragment t = new InstantaneousDataFragment();

        t.halt(alertBuilder);
        Toast.makeText(getBaseContext(), "Hello", Toast.LENGTH_LONG).show();

    }

}
