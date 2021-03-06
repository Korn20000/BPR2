package com.example.admin.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.gigamole.library.PulseView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ProgressBar myProgressBar;
    private TextView mainValue;
    private BluetoothLeScannerCompat mScanner;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private boolean mScanning;
    private Handler mHandler;
    private boolean isUserClickedBackButton = false;
    private TextView statusMessages;
    private String deviceAddress;
    private TextView measurementUnit;
    private int stateId = STATE_SCANNING;
    private boolean BTReadyForRandom = false;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;
    private static final int STATE_SCANNING_STOPPED = 3;
    private static final int STATE_READY_TO_MEASURE = 4;
    private static final int STATE_SCANNING = 5;
    private static final int STATE_DISCOVERING_SERVICES = 6;
    private static final int STATE_SERVICES_DISCOVERED = 7;
    private static final int STATE_WRITING_DESCRIPTOR = 8;
    private static final int STATE_SENDING_VALUE_REQUEST = 9;
    private static final int STATE_VALUE_RECEIVED = 10;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("GATT", "Connected. Looking for services...");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stateId = STATE_CONNECTED;
                        updateStatusMessage();
                    }
                });
                gatt.discoverServices();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stateId = STATE_DISCOVERING_SERVICES;
                        updateStatusMessage();
                    }
                });
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stateId = STATE_DISCONNECTED;
                        updateStatusMessage();
                    }
                });
                Log.d("GATT","Disconnected !!!");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Log.d("GATT","Error! " + status);
                return;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    stateId = STATE_SERVICES_DISCOVERED;
                    updateStatusMessage();
                }
            });

            // Get the notify characteristic
            BluetoothGattCharacteristic characteristic = gatt.getService(GattAttributes.BLE_GLUCOSE_METER_SERVICE)
                    .getCharacteristic(GattAttributes.BLE_NOTIFY_RANDOM_VALUE);

            // Enable notifications for this characteristic locally
            gatt.setCharacteristicNotification(characteristic, true);

            // Write on the config descriptor to be notified when the value changes
            BluetoothGattDescriptor descriptor =
                    characteristic.getDescriptor(GattAttributes.NOTIFY_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    stateId = STATE_WRITING_DESCRIPTOR;
                    updateStatusMessage();
                }
            });
            gatt.writeDescriptor(descriptor);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {
            if (GattAttributes.NOTIFY_DESCRIPTOR.equals(descriptor.getUuid())) {
                BluetoothGattCharacteristic characteristic = gatt.getService(GattAttributes.BLE_GLUCOSE_METER_SERVICE)
                        .getCharacteristic(GattAttributes.BLE_NOTIFY_RANDOM_VALUE);
                gatt.readCharacteristic(characteristic);
            }
            BTReadyForRandom = true;
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            readLevelCharacteristic(characteristic);

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            readLevelCharacteristic(characteristic);
        }

    };

    private void readLevelCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (GattAttributes.BLE_NOTIFY_RANDOM_VALUE.equals(characteristic.getUuid()) && BTReadyForRandom) {
            writeCustomCharacteristic();
            byte[] data = characteristic.getValue();
            final float randomMeasurement = data[0];
            Log.d("randomMeasurement is: ", String.valueOf(randomMeasurement));
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mainValue.setText(String.valueOf(randomMeasurement));
                    measurementUnit.setVisibility(View.VISIBLE);
                    stateId = STATE_VALUE_RECEIVED;
                    updateStatusMessage();
                }
            });
            BTReadyForRandom = false;
        }
    }

    public void writeCustomCharacteristic() {
        stateId = STATE_SENDING_VALUE_REQUEST;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateStatusMessage();
            }
        });
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w("BT Error: ", "BluetoothAdapter not initialized");
            return;
        }
        /*check if the service is available on the device*/
        BluetoothGattService mCustomService = mBluetoothGatt.getService(GattAttributes.BLE_GLUCOSE_METER_SERVICE);
        if(mCustomService == null){
            Log.w("WriteRToArduino", "Custom BLE Service not found");
            return;
        }
        Log.d("BT service is:", mCustomService.getUuid().toString());

        /*get the write characteristic from the service*/
        BluetoothGattCharacteristic mWriteCharacteristic = mCustomService.getCharacteristic(GattAttributes.BLE_WRITE_R_GET_RANDOM_VALUE);
        mWriteCharacteristic.setValue('r', BluetoothGattCharacteristic.FORMAT_UINT8,0);
        Log.d("write char is:", mWriteCharacteristic.getProperties()  + "     VALUE: " + mWriteCharacteristic.getStringValue(0));
        if(!mBluetoothGatt.writeCharacteristic(mWriteCharacteristic)){
            Log.w("WriteRToArduino", "Failed to write characteristic");
        }
    }

    private void scanOurGMLeDevice(final boolean enable) {
        mScanner = BluetoothLeScannerCompat.getScanner();
        ScanSettings settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(1000).setUseHardwareBatchingIfSupported(false).build();
        ScanFilter scanFilter = new ScanFilter.Builder().setServiceUuid(new ParcelUuid(GattAttributes.BLE_GLUCOSE_METER_SERVICE)).build();
        stateId = STATE_SCANNING;
        updateStatusMessage();
        if (enable && mScanner != null) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mScanner.stopScan(mScanCallback);
                    if(!mScanning && deviceAddress == null) {
                        statusMessages.setText(R.string.device_not_found);
                        myProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        setProgressAnimate(100);
                    }
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mScanner.startScan(Arrays.asList(scanFilter), settings, mScanCallback);
        } else {
            mScanning = false;
            stateId = STATE_SCANNING_STOPPED;
            updateStatusMessage();
            if(mScanner != null) {
                mScanner.stopScan(mScanCallback);
            }
        }
        invalidateOptionsMenu();
    }

    private final ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            if (!results.isEmpty()) {
                ScanResult result = results.get(0);
                Log.d("GM is: ", result.toString());
                BluetoothDevice device = result.getDevice();
                Log.d("device's address is: ", device.getAddress());
                deviceAddress = device.getAddress();
                scanOurGMLeDevice(false);
                stateId = STATE_READY_TO_MEASURE;
                updateStatusMessage();
            }
            else {
                //Toast.makeText(MainActivity.super.getApplicationContext(), R.string.GM_not_found, Toast.LENGTH_SHORT).show();
                //if(mScanning) { statusMessages.setText("Scanning..."); }
                updateStatusMessage();
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            //Toast.makeText(MainActivity.super.getApplicationContext(), R.string.GM_not_found, Toast.LENGTH_SHORT).show();
            statusMessages.setText(R.string.scanning_error + errorCode);
        }
    };

    private void setProgressAnimate(int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(myProgressBar, "progress", myProgressBar.getProgress(), (progressTo * 100));
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }

    private void updateStatusMessage () {
        if(stateId == STATE_CONNECTING) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(15);
            statusMessages.setText(R.string.connecting);
        }
        if(stateId == STATE_CONNECTED) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(30);
            statusMessages.setText(R.string.connected);
        }
        if(stateId == STATE_DISCONNECTED) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(100);
            statusMessages.setText(R.string.disconnected);
        }
        if(stateId == STATE_SCANNING_STOPPED) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(100);
            statusMessages.setText(R.string.scanning_interrupted);
        }
        if(stateId == STATE_READY_TO_MEASURE) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(100);
            statusMessages.setText(R.string.ready_to_measure);
        }
        if(stateId == STATE_SCANNING) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(50);
            statusMessages.setText(R.string.scanning);
        }
        if(stateId == STATE_DISCOVERING_SERVICES) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(45);
            statusMessages.setText(R.string.services_discovery);
        }
        if(stateId == STATE_SERVICES_DISCOVERED) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(60);
            statusMessages.setText(R.string.service_discovered);
        }
        if(stateId == STATE_WRITING_DESCRIPTOR) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(75);
            statusMessages.setText(R.string.writing_descriptor);
        }
        if(stateId == STATE_SENDING_VALUE_REQUEST) {
            myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            setProgressAnimate(90);
            statusMessages.setText(R.string.requesting_value);
        }
        if(stateId == STATE_VALUE_RECEIVED) {
            float mainValueRead = Float.parseFloat((String)mainValue.getText());
            if (mainValueRead <= 4.5) {
                myProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.very_low_level), PorterDuff.Mode.SRC_IN);
                setProgressAnimate(100);
                statusMessages.setText(R.string.glucose_level_very_low);
            }
            if (4.5 < mainValueRead && 7.0 >= mainValueRead) {
                myProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.mid_low_level), PorterDuff.Mode.SRC_IN);
                setProgressAnimate(100);
                statusMessages.setText(R.string.glucose_level_low);
            }
            if (7.0 < mainValueRead && 8.0 >= mainValueRead) {
                myProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.OK_level), PorterDuff.Mode.SRC_IN);
                setProgressAnimate(100);
                statusMessages.setText(R.string.glucose_level_optimal);
            }
            if (8.0 < mainValueRead && 10.5 >= mainValueRead) {
                myProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.mid_high_level), PorterDuff.Mode.SRC_IN);
                setProgressAnimate(100);
                statusMessages.setText(R.string.glucose_level_high);
            }
            if (10.5 < mainValueRead) {
                myProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.very_high_level), PorterDuff.Mode.SRC_IN);
                setProgressAnimate(100);
                statusMessages.setText(R.string.glucose_level_very_low);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        statusMessages = findViewById(R.id.statusMessages);
        mainValue = findViewById(R.id.mainValue);
        measurementUnit = findViewById(R.id.measurement_unit);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        mainValue.setTypeface(myCustomFont);
        measurementUnit.setTypeface(myCustomFont);
        myProgressBar = findViewById(R.id.myprogressbar);

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(mBluetoothAdapter.isEnabled()) {
            scanOurGMLeDevice(true);
        }

/*        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Log.d("onCreate: ","Enable bluetooth");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PulseView pulseView = findViewById(R.id.bloodDrop);
        pulseView.startPulse();

        pulseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deviceAddress != null) {
                    measurementUnit.setVisibility(View.INVISIBLE);
                    mainValue.setText(R.string.measuring);
                    stateId = STATE_CONNECTING;
                    updateStatusMessage();
                    BluetoothDevice deviceToConnect = mBluetoothAdapter.getRemoteDevice(deviceAddress);
                    mBluetoothGatt = deviceToConnect.connectGatt(getApplicationContext(), false, mGattCallback);

                } else {
                    Toast.makeText(MainActivity.super.getApplicationContext(), "Wait for scanning to finish !", Toast.LENGTH_SHORT).show();
                }


                //pulseView.setEnabled(false);

                //MyAsyncTask myAsyncTask = new MyAsyncTask();
                //myAsyncTask.execute();

            }
        });



/*        Button sendchar = findViewById(R.id.sendchar);
        sendchar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeCustomCharacteristic('r');
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (!mScanning){ scanOurGMLeDevice(true); }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            if (mBluetoothAdapter != null && !mScanning) {
                scanOurGMLeDevice(true);
                Log.d("Rescan after pause:","scanning...");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanOurGMLeDevice(false);
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
        //unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanOurGMLeDevice(false);
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        //unbindService(mServiceConnection);
        //mBluetoothLeService = null;
    }

    @Override
    public void onBackPressed() {

     if(!isUserClickedBackButton)
     {
         Toast.makeText(this, "press back button again to exit", Toast.LENGTH_LONG).show();
         isUserClickedBackButton = true;
     }else
     {
         super.onBackPressed();
     }     new CountDownTimer(3000,1000)
     {
         @Override
         public void onTick(long l) {
         }

         @Override
         public void onFinish() {
            isUserClickedBackButton= false;
         }
     }.start();

 }

    //creates the method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action
        // bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //handle actions

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.info:
               // Log.i(TAG, "start settings activity");
                Intent intent = new Intent(MainActivity.this, Info.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
      // Handle navigation view item clicks here.
      int id = item.getItemId();

      if (id == R.id.nav_history) {

          Intent searchIntent = new Intent(MainActivity.this, History.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
      } else if (id == R.id.nav_graph) {

          Intent searchIntent = new Intent(MainActivity.this, Graph.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

      } else if (id == R.id.nav_help) {
          Intent searchIntent = new Intent(MainActivity.this, Help.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
      }else if (id == R.id.nav_setting)
      {
          Intent searchIntent = new Intent(MainActivity.this, Setting.class);
          startActivity(searchIntent);
          overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
      }

      DrawerLayout drawer = findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
  }


  /*---------------------------------------------------COMMENTED_CODE_SECTION---------------------------------------------------*/

      /*public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            myProgressBar.setVisibility(View.VISIBLE);
            myProgressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<100; i++){
                publishProgress(i);
                SystemClock.sleep(100);
                pulseView.finishPulse();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            myProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //myProgressBar.setVisibility(View.GONE);
            pulseView.setEnabled(true);
        }
    }*/


 /*   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/


 /*   private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                statusMessages.setText(R.string.connected);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                statusMessages.setText(R.string.disconnected);
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                statusMessages.setText(R.string.service_discovered);
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                mainValue.setText(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };*/

    /*private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("On Service connected: ", "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(deviceAddress);
            mBluetoothLeService.registerNotifyCharacteristics();
            Intent gattServiceIntent = new Intent(getParent(), BluetoothLeService.class);
            bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };*/
/*    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }*/
}
