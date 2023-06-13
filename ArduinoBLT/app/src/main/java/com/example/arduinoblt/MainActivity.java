package com.example.arduinoblt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    TextView textStatus;
    Button btnParied, btnSearch, btnSendA,btnSendB,btnSendC,btnSendD,btnStart,btnGo,btnReset;
    ListView listView;
    TextView text1;
    TextView text2;

    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;

    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 블루투스 권한 허용
        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(MainActivity.this, permission_list, 1);

        // 블루투스 확성화 하는 부분
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // 변수들
        textStatus = (TextView) findViewById(R.id.text_status);
        btnParied = (Button) findViewById(R.id.btn_paired);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSendA = (Button) findViewById(R.id.btn_sendA);
        btnSendB = (Button) findViewById(R.id.btn_sendB);
        btnSendC = (Button) findViewById(R.id.btn_sendC);
        btnSendD = (Button) findViewById(R.id.btn_sendD);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnGo = (Button) findViewById(R.id.btn_go);
        btnReset = (Button) findViewById(R.id.btn_reset);
        listView = (ListView) findViewById(R.id.listview);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text1.setText("");
        text2.setText("");
        // 연결된 것들 보여주기
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        listView.setAdapter(btArrayAdapter);

        // 리스트 뷰에서 선택해서 연결하기
        listView.setOnItemClickListener(new myOnItemClickListener());
    }

    public void onClickButtonPaired(View view){
        btArrayAdapter.clear();
        if(deviceAddressArray!=null && !deviceAddressArray.isEmpty()){ deviceAddressArray.clear(); }
        pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }
    }

    public void onClickButtonSearch(View view){
        // Check if the device is already discovering
        if(btAdapter.isDiscovering()){
            btAdapter.cancelDiscovery();
        } else {
            if (btAdapter.isEnabled()) {
                btAdapter.startDiscovery();
                btArrayAdapter.clear();
                if (deviceAddressArray != null && !deviceAddressArray.isEmpty()) {
                    deviceAddressArray.clear();
                }
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver, filter);
            } else {
                Toast.makeText(getApplicationContext(), "bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // 연결된거에 데이터보내기
    public void onClickButtonSendA(View view){
        String starts = text1.getText().toString();
        if(starts.equals(""))
            text1.setText("건물1");
        else
            text2.setText("건물1");
    }
    public void onClickButtonSendB(View view){
        String starts = text1.getText().toString();
        if(starts.equals(""))
            text1.setText("건물2");
        else
            text2.setText("건물2");

    }
    public void onClickButtonSendC(View view){
        String starts = text1.getText().toString();
        if(starts.equals(""))
            text1.setText("건물3");
        else
            text2.setText("건물3");
    }
    public void onClickButtonSendD(View view){
        String starts = text1.getText().toString();
        if(starts.equals(""))
            text1.setText("건물4");
        else
            text2.setText("건물4");
    }
    public void onClickButtonGo(View view){
        String starts = text1.getText().toString();
        String ends = text2.getText().toString();
        if(starts == "기본위치" && ends == "건물1"){
            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("1"); }
        }
        else if(starts == "기본위치" && ends == "건물2"){
            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("2"); }
        }
        else if(starts == "기본위치" && ends == "건물3"){
            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("3"); }
        }
        else if(starts == "기본위치" && ends == "건물4"){
            Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("4"); }
        }

        else if(starts == "건물1" && ends == "기본위치"){
            Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("5"); }
        }
        else if(starts == "건물1" && ends == "건물2"){
            Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("6"); }
        }
        else if(starts == "건물1" && ends == "건물3"){
            Toast.makeText(getApplicationContext(), "7", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("7"); }
        }
        else if(starts == "건물1" && ends == "건물4"){
            Toast.makeText(getApplicationContext(), "8", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("8"); }
        }
        else if(starts == "건물2" && ends == "기본위치"){
            Toast.makeText(getApplicationContext(), "9", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("9"); }
        }
        else if(starts == "건물2" && ends == "건물1"){
            Toast.makeText(getApplicationContext(), "10", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("10"); }
        }
        else if(starts == "건물2" && ends == "건물3"){
            Toast.makeText(getApplicationContext(), "11", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("11"); }
        }
        else if(starts == "건물2" && ends == "건물4"){
            Toast.makeText(getApplicationContext(), "12", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("12"); }
        }
        else if(starts == "건물3" && ends == "기본위치"){
            Toast.makeText(getApplicationContext(), "13", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("13"); }
        }
        else if(starts == "건물3" && ends == "건물1"){
            Toast.makeText(getApplicationContext(), "14", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("14"); }
        }
        else if(starts == "건물3" && ends == "건물2"){
            Toast.makeText(getApplicationContext(), "15", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("15"); }
        }
        else if(starts == "건물3" && ends == "건물4"){
            Toast.makeText(getApplicationContext(), "16", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("16"); }
        }
        else if(starts == "건물4" && ends == "기본위치"){
            Toast.makeText(getApplicationContext(), "17", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("17"); }
        }
        else if(starts == "건물4" && ends == "건물1"){
            Toast.makeText(getApplicationContext(), "18", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("18"); }
        }
        else if(starts == "건물4" && ends == "건물2"){
            Toast.makeText(getApplicationContext(), "19", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("19"); }
        }
        else if(starts == "건물4" && ends == "건물3"){
            Toast.makeText(getApplicationContext(), "20", Toast.LENGTH_SHORT).show();
            if(connectedThread!=null){ connectedThread.write("20"); }
        }

    }
    public void onClickButtonSetStart(View view){
        String starts = text1.getText().toString();
        if(starts == "")
            text1.setText("기본위치");
        else
            text2.setText("기본위치");
    }
    public void onClickButtonReset(View view){
        text1.setText("");
        text2.setText("");
    }
    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
                btArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

    public class myOnItemClickListener implements AdapterView.OnItemClickListener {
    /*
    리스트 뷰에서 하나 선택했을때
    저 선택된 기기의 이름과 address를 가져오고,
    address로 BluetoothDevice를 만들고, 그 기기와의 소켓을 만들고 연결을 시도
    socket이 정상적으로 만들어지고 연결이 되었다면 flag가 true. 그러면 thread를 통해서 블루투스 통신을 시작.
     */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();

            textStatus.setText("try...");

            final String name = btArrayAdapter.getItem(position); // get name
            final String address = deviceAddressArray.get(position); // get address
            boolean flag = true;

            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            // create & connect socket
            try {
                btSocket = createBluetoothSocket(device);
                btSocket.connect();
            } catch (IOException e) {
                flag = false;
                textStatus.setText("connection failed!");
                e.printStackTrace();
            }

            // start bluetooth communication
            if(flag){
               textStatus.setText("connected to "+name);
                connectedThread = new ConnectedThread(btSocket);
                connectedThread.start();
            }

        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }
}