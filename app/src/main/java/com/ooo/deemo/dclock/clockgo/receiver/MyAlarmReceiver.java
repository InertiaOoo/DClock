package com.ooo.deemo.dclock.clockgo.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.ooo.deemo.dclock.clockgo.bean.ClockBean;
import com.ooo.deemo.dclock.clockgo.service.MyForService;
import com.ooo.deemo.dclock.clockgo.utils.ClockUtils;

import java.util.Calendar;
import java.util.List;

public class MyAlarmReceiver extends BroadcastReceiver {


    AlarmManager alarmManager = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("MyAlarmReceiver","onReceive");

        if (intent.getAction().equals("com.ooo.deemo.RING")){


            List<ClockBean> clockBeanList = ClockUtils.getClockBean(context);

            for (int i = 0;i<clockBeanList.size();i++){

                if(clockBeanList.get(i).isSet()){


                }else {

                    Log.e("MyAlarmReceiver","onReceive:setrepeatclock");

                    alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    Intent intent1 = new Intent(context, MyForService.class);

                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                              pendingIntent = PendingIntent.getForegroundService(context, clockBeanList.get(i).getId(),
                                intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    }else{
                           pendingIntent = PendingIntent.getService(context, clockBeanList.get(i).getId(),
                                intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    }


                    Log.e("ClockUtils",""+ClockUtils.getClockBean(context).get(i).getTimeInMillis());
                    Calendar c = Calendar.getInstance();
                    Log.e("Calendar",""+c.getTimeInMillis());

                   alarmManager.setRepeating( AlarmManager.RTC_WAKEUP,clockBeanList.get(i).getTimeInMillis(),24*60*60*1000,pendingIntent);




                    clockBeanList.get(i).setSet(true);

                    ClockUtils.putClockBean(context,clockBeanList);
                }

            }





            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        }}
}
