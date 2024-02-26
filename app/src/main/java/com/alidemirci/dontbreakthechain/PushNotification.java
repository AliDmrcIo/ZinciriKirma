package com.alidemirci.dontbreakthechain;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PushNotification extends Worker { //bu sınıf bizim notification'ımızı arkaplanda, uygulama kapanmış dahi olsa kullanıcıya göndermemizi sağlayacak.

    Context context;

    public PushNotification(@NonNull Context context, @NonNull WorkerParameters workerParams) { //Constructorla çok bir işimiz yok, eğer context almamız gerekirse diye oluşturmak zorunda olduğumuz için oluşturduk
        super(context, workerParams);
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork() { //esas işi yapacağımız yer. uygulama kapansa dahi arka planda çalışması gereken şeyleri buraya yazıcaz
        //Data data = getInputData(); //diğer class'lardan veya fragmentlardan?? veri almayı böyle yapabiliriz. Bize bir veri verilecek, onu al demek.
        //int workerIcinGelenDegeriAl=data.getInt("intKey",0); //intkey anahtarkelimeli diğer classlardan gelen değeri al, yoksa sıfır olarak al. bu gelen değeri de burada kullanabiliriz


        makeNotification();

        return Result.success();
    }

    public void makeNotification(){

        String chanelID="CHANNEL_ID_NOTIFICATION";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,chanelID);

        builder.setSmallIcon(R.drawable.zincirfoto)
                .setContentTitle("Zinciri Kırma")
                .setContentText("Hey! Zincirini Tamamlamana Son 2 Saat!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context,StartingPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data","data");

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_MUTABLE);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(chanelID);
            if(notificationChannel==null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(chanelID,"some description", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }
        notificationManager.notify(0,builder.build());

    }
}
