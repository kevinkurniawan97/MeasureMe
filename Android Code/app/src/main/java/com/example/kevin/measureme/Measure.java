package com.example.kevin.measureme;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.util.HashMap;
import java.util.Map;

public class Measure extends AppCompatActivity {

    public final String ACTION_USB_PERMISSION = "com.example.kevin.measureme.USB_PERMISSION";

    private static final String LEN_PREFIX = "Count_";
    private static final String VAL_PREFIX = "IntValue_";

    SharedPreferences prefsUser, prefsSize;
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;

    Button button;
    TextView status, instruction1, instruction2, instruction3;

    private int measureMode = 1; // 1: shoulder, 2: width, 3: thickness
    int shoulder;
    int[] widthResult;
    int[] thickResult;
    int countResult=0;
    BodySize bodySize;
    DBHelper mydb;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        mydb = new DBHelper(this);
        prefsUser = getSharedPreferences("user", 0);
        prefsSize = getSharedPreferences("sizedetails", 0);

        widthResult=new int[2];
        thickResult=new int[2];

        resetResult();

        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        image = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.buttonReady);
        status = (TextView) findViewById(R.id.textView4);
        instruction1 = (TextView) findViewById(R.id.textView);
        instruction2 = (TextView) findViewById(R.id.textView2);
        instruction3 = (TextView) findViewById(R.id.textView3);
        start();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);
        start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte mode=0;
                if(measureMode==1) {
                    mode=1;
                } else if(measureMode==2) {
                    mode=2;
                } else if(measureMode==3) {
                    mode=3;
                }

                if(serialPort != null) {
                    button.setEnabled(false);
                    sendTrigger(mode);
                    status.setText("Measuring...");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device not connected.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = "";
            try {
                for(int i=0; i<(arg0.length); i++) {
                    data += Integer.toHexString(arg0[i]);
                }
                final int x = Integer.parseInt(data, 16);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(measureMode==1) {
                            shoulder=x;
                            status.setText("MeasureMode: "+measureMode);
                            measureMode = 2;
                            button.setEnabled(true);
                            playBeep();
                            instruction2.setText("Now, raise your hands! Get your position into this:");
                            instruction3.setVisibility(View.INVISIBLE);
                            image.setImageResource(R.drawable.measure_mode_2);
                        }
                        else if(measureMode==2) {
                            widthResult[countResult]=x;
                            status.setText("MeasureMode: "+measureMode);
                            countResult++;
                            if (countResult==2) {
                                measureMode=3;
                                countResult=0;
                                button.setEnabled(true);
                                playBeep();
                                instruction2.setText("Next, we will measure your body part thickness. Get your position into this:");
                                image.setImageResource(R.drawable.measure_mode_3);
                            }

                        } else if(measureMode==3) {
                            thickResult[countResult]=x;
                            status.setText("MeasureMode: "+measureMode);
                            countResult++;
                            if (countResult==2) {
                                countResult=0;
                                calculateMeasurement();
                                playBeep();
                                stop();
                            }
                        }
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    };

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
                            serialPort.setBaudRate(9600);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);
                            Toast.makeText(getApplicationContext(), "Device connected.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.d("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.d("SERIAL", "PERM NOT GRANTED");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                start();
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                stop();
            }
        }
    };

    public void start() {
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == 0x2341)// Arduino Vendor ID
                {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }
                if (!keep)
                    break;
            }
        }
    }

    public void calculateMeasurement() {
        int genderCode = prefsUser.getInt("gender",0);

        int[] male_chest_cloth_size = getFromPrefs("male_chest_cloth_size");
        int[] male_waist_cloth_size = getFromPrefs("male_waist_cloth_size");
        int[] female_chest_cloth_size = getFromPrefs("female_chest_cloth_size");
        int[] female_waist_cloth_size = getFromPrefs("female_waist_cloth_size");

        bodySize = new BodySize(shoulder, widthResult, thickResult, genderCode,
                male_chest_cloth_size, male_waist_cloth_size,
                female_chest_cloth_size, female_waist_cloth_size);

        bodySize.measureSize();
        String name = prefsUser.getString("name","");
        String gender;
        if(genderCode==1){
            gender = "Male";
        } else {
            gender = "Female";
        }
        int shoulder = bodySize.getShoulderWidth();
        double chest = bodySize.getChestRound();
        double waist = bodySize.getWaistRound();
        String size = bodySize.getClothSize();

        if(mydb.insertHistory(name, gender, shoulder, chest, waist, size)) {
            Toast.makeText(getApplicationContext(), "Measurement result added to history.",
                    Toast.LENGTH_SHORT).show();
        }

        int numRows = mydb.numberOfRows();
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", numRows);

        Intent intent = new Intent(getApplicationContext(),DisplayHistory.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void resetResult() {
        shoulder=0;
        for(int i=0; i<2; i++) {
            widthResult[i]=0;
            thickResult[i]=0;
        }
    }

    public void sendTrigger(byte mode) {
        byte[] byteData = new byte[] {mode};
        serialPort.write(byteData);
    }

    public void stop() {
        serialPort.close();
    }

    private boolean checkData(int[] tempVal) {
        boolean complete=true;
        for(int i=0; i<20; i++) {
            if(tempVal[i]==0) {
                complete=false;
                break;
            }
        }
        return complete;
    }

    public int[] getFromPrefs(String name){
        int[] ret;
        int count = prefsSize.getInt(LEN_PREFIX + name, 0);
        ret = new int[count];
        for (int i = 0; i < count; i++){
            ret[i] = prefsSize.getInt(VAL_PREFIX+ name + i, i);
        }
        return ret;
    }

    public void playBeep() {
        MediaPlayer m = new MediaPlayer();
        try {
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = getAssets().openFd("beep.wav");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}