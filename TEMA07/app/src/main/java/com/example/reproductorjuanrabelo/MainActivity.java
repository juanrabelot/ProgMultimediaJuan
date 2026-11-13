package com.example.reproductorjuanrabelo;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ExoPlayer player;
    private Button btnPlay, btnPausa;
    private SeekBar seekBar;
    private TextView tvActual, tvTotal;
    private SoundPool soundPool;
    private Button btnEfectoIphobe, btnSamsung, btnNokia, btnSkype;
    private int id1, id2, id3, id4;
    private int sonidosCargados = 0;
    private boolean arrastrando = false;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable actualizar = new Runnable() {
        @Override
        public void run() {
            if (player != null && !arrastrando) {
                long posicion = player.getCurrentPosition();
                seekBar.setProgress((int) posicion);
                tvActual.setText(formatear(posicion));
            }
            handler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnPlay = findViewById(R.id.btnPlay);
        btnPausa = findViewById(R.id.btnPausa);
        seekBar = findViewById(R.id.seekBar);
        tvActual = findViewById(R.id.tvActual);
        tvTotal = findViewById(R.id.tvTotal);

        btnEfectoIphobe = findViewById(R.id.btnEfectoIphobe);
        btnSamsung = findViewById(R.id.btnSamsung);
        btnNokia = findViewById(R.id.btnNokia);
        btnSkype = findViewById(R.id.btnSkype);

        AudioAttributes atributos = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)   //hasta 4 a la vez
                .setAudioAttributes(atributos)
                .build();
        id1 = soundPool.load(this, R.raw.iphone, 1);
        id2 = soundPool.load(this, R.raw.samsung, 1);
        id3 = soundPool.load(this, R.raw.nokia, 1);
        id4 = soundPool.load(this, R.raw.skype, 1);

        //load() trabaja en segundo plano: avisa al terminar
        soundPool.setOnLoadCompleteListener((pool, sampleId, status) -> {
            if (status == 0) {
                sonidosCargados++;
                if (sonidosCargados >= 4) {
                    btnEfectoIphobe.setEnabled(true);
                    btnSamsung.setEnabled(true);
                    btnNokia.setEnabled(true);
                    btnSkype.setEnabled(true);
                }
            }
        });

        btnEfectoIphobe.setOnClickListener(v -> soundPool.play(id1, 1f, 1f, 1, 0, 1f));
        btnSamsung.setOnClickListener(v -> soundPool.play(id2, 1f, 1f, 1, 0, 1f));
        btnNokia.setOnClickListener(v -> soundPool.play(id3, 1f, 1f, 1, 0, 1f));
        btnSkype.setOnClickListener(v -> soundPool.play(id4, 1f, 1f, 1, 0, 1f));
        //En onDestroy:soundPool.release();

        player = new ExoPlayer.Builder(this).build();
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.jay); //
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int estado) {
                if (estado == Player.STATE_READY) {
                    long duracion = player.getDuration();
                    seekBar.setMax((int) duracion);
                    tvTotal.setText(formatear(duracion));
                }
            }
        });

        btnPlay.setOnClickListener(v -> {
            if (player.getPlaybackState() == Player.STATE_ENDED) {
                player.seekTo(0);
            }
            player.play();
        });

        btnPausa.setOnClickListener(v -> player.pause());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar barra) {
                arrastrando = true;
            }

            @Override
            public void onProgressChanged(SeekBar barra, int progreso, boolean delUsuario) {
                if (delUsuario) {
                    tvActual.setText(formatear(progreso));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar barra) {
                player.seekTo(barra.getProgress());
                arrastrando = false;
            }
        });
    }

    private String formatear(long ms) {
        long segundos = ms / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d", segundos / 60, segundos % 60);
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(actualizar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player.isPlaying()) {
            player.pause();
        }
        handler.removeCallbacks(actualizar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
        if (soundPool != null) {
            soundPool.release();
        }
    }
}
