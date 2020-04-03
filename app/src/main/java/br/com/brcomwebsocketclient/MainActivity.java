package br.com.brcomwebsocketclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button clientOn, clientReconnect, clientClose, clientSend, clientFoto;
    private final int ZXING_CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientOn = findViewById(R.id.clientConnect);
        clientReconnect = findViewById(R.id.clientReconnect);
        clientClose = findViewById(R.id.clientClose);
        clientSend = findViewById(R.id.clientSend);
        clientFoto = findViewById(R.id.clienttirarFoto);


        clientOn.setOnClickListener(v -> WebSocketSSLClient.createConnection());

        clientReconnect.setOnClickListener(v -> WebSocketSSLClient.reconnectSocket());

        clientClose.setOnClickListener(v -> WebSocketSSLClient.closeSocket());

        clientSend.setOnClickListener(v -> WebSocketSSLClient.sendMessageSocket("Teste"));

        clientFoto.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
            } else {
                WebSocketSSLClient.sendMessageSocket("sendBarCode");
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ZXING_CAMERA_PERMISSION)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) WebSocketSSLClient.sendMessageSocket("sendBarCode");
    }
}
