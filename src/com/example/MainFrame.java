package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

  public MainFrame() {
    super("Ambient App");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLayout(new BorderLayout());

    List<ChillSound> soundList = new ArrayList<>();
    soundList.add(new ChillSound("sounds/雨.wav", "雨"));
    soundList.add(new ChillSound("sounds/波.wav", "波"));
    soundList.add(new ChillSound("sounds/風.wav", "風"));
    soundList.add(new ChillSound("sounds/ウミネコ.wav", "ウミネコ"));
    soundList.add(new ChillSound("sounds/ツクツクボウシ.wav", "ツクツクボウシ"));
    soundList.add(new ChillSound("sounds/食卓.wav", "食卓"));
    soundList.add(new ChillSound("sounds/スズメ.wav", "スズメ"));
    soundList.add(new ChillSound("sounds/学校の廊下.wav", "学校の廊下"));
    soundList.add(new ChillSound("sounds/カラス.wav", "カラス"));
    soundList.add(new ChillSound("sounds/風鈴.wav", "風鈴"));
    

        // パネルの作成
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    // soundList の各要素に対して UI を動的に生成する
    for (ChillSound sound : soundList) {
        // 個別のパネルを作成（タイトル付きの枠で音源名を表示）
        JPanel soundPanel = new JPanel(new BorderLayout());
        soundPanel.setBorder(BorderFactory.createTitledBorder(sound.getName()));

        // 再生・停止ボタンのパネルを作成
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton playButton = new JButton("Play " + sound.getName());
        playButton.addActionListener(e -> sound.play());
        JButton stopButton = new JButton("Stop " + sound.getName());
        stopButton.addActionListener(e -> sound.stop());
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);

        // 音量調整用スライダーを作成
        JSlider volumeSlider = new JSlider(0, 100, (int) (sound.getVolume() * 100));
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> {
            int value = volumeSlider.getValue();
            sound.setVolume(value / 100.0f);
        });

        // soundPanel にボタンパネルとスライダーを配置
        soundPanel.add(buttonPanel, BorderLayout.NORTH);
        soundPanel.add(volumeSlider, BorderLayout.CENTER);

        // メインパネルに追加
        mainPanel.add(soundPanel);
    }

    // 音源が多い場合のため、スクロール可能なパネルとして追加
    JScrollPane scrollPane = new JScrollPane(mainPanel);
    add(scrollPane, BorderLayout.CENTER);
  }
}
