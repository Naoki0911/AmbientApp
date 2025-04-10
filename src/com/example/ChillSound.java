package com.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ChillSound implements Sound {
    private Clip clip;
    private FloatControl volumeControl;
    private float volume = 0.5f; // 初期音量
    private String name;

    public ChillSound(String filePath, String name) {
    	this.name = name;
        try {
            // 音声ファイルを読み込む
        	//AudioInputStreamは音声データが入力されたストリームのこと
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            // aisから音声データを受け取り、Clipオブジェクトにロードする
            clip.open(ais);
            // 音量調整用のコントロールを取得
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volume); // 初期音量を設定
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        if (clip != null) {
        	// Clip.LOOP_CONTINUOUSLY を引数に渡すことで、音声が無限に繰り返し再生されるように設定
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
            clip.start();
        }
    }

    @Override
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
        if (volumeControl != null) {
            // 音量0.0～1.0の値を、デシベルの範囲に変換して設定
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float dB = min + (max - min) * volume;
            volumeControl.setValue(dB);
        }
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public String getName() {
        return name;
    }
}
