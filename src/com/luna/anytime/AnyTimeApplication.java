package com.luna.anytime;

import com.avos.avoscloud.AVOSCloud;

import android.app.Application;

public class AnyTimeApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		AVOSCloud.useAVCloudCN();
		// U need your AVOS key and so on to run the code.
		AVOSCloud.initialize(this,
				"70l90kzm53nplnu013ala0j8wipr594d36m5zuz94ukvmh5s",
				"lamrsuheyiaqcx4o7m3jaz4awaeukerit1mucnjwk7ibokfv");
	}
}
