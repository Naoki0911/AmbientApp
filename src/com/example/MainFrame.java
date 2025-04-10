package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Ambient App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ウィンドウサイズは必要に応じて調整してください
        setSize(1200, 900);
        setLayout(new BorderLayout());

        // カテゴリーごとに音源リストを用意
        List<ChillSound> naturalSounds = new ArrayList<>();
        naturalSounds.add(new ChillSound("sounds/雨.wav", "雨"));
        naturalSounds.add(new ChillSound("sounds/波.wav", "波"));
        naturalSounds.add(new ChillSound("sounds/風.wav", "風"));

        List<ChillSound> animalSounds = new ArrayList<>();
        animalSounds.add(new ChillSound("sounds/ウミネコ.wav", "ウミネコ"));
        animalSounds.add(new ChillSound("sounds/ツクツクボウシ.wav", "ツクツクボウシ"));
        animalSounds.add(new ChillSound("sounds/スズメ.wav", "スズメ"));
        animalSounds.add(new ChillSound("sounds/カラス.wav", "カラス"));

        List<ChillSound> ambientSounds = new ArrayList<>();
        ambientSounds.add(new ChillSound("sounds/風鈴.wav", "風鈴"));
        ambientSounds.add(new ChillSound("sounds/食卓.wav", "食卓"));
        ambientSounds.add(new ChillSound("sounds/学校の廊下.wav", "学校の廊下"));

        // 横並びにカテゴリーセクションを配置するパネル
        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.X_AXIS));

        // カテゴリーのセクションを追加
        categoriesPanel.add(createCategorySection("自然", naturalSounds));
        categoriesPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        categoriesPanel.add(createCategorySection("動物の鳴き声", animalSounds));
        categoriesPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        categoriesPanel.add(createCategorySection("その他", ambientSounds));

        // 全体をスクロール可能にする（横スクロールも有効）
        JScrollPane scrollPane = new JScrollPane(categoriesPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 指定されたカテゴリー名と音源リストからセクションパネルを作成する
     */
    private JPanel createCategorySection(String categoryName, List<ChillSound> sounds) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));

        // カテゴリーの見出しラベル
        JLabel header = new JLabel(categoryName);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionPanel.add(header);

        // 少し余白を入れる
        sectionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // カテゴリー内の音源パネルを生成（下記メソッドを利用）
        JPanel categoryPanel = createCategoryPanel(sounds);
        categoryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionPanel.add(categoryPanel);

        // セクション下部の余白
        sectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        return sectionPanel;
    }

    /**
     * 指定された音源リストから、各音源パネルを縦に並べたパネルを生成する
     */
    private JPanel createCategoryPanel(List<ChillSound> sounds) {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));

        for (ChillSound sound : sounds) {
            // 各音源用のパネル作成
            JPanel soundPanel = new JPanel(new BorderLayout());
            soundPanel.setBorder(BorderFactory.createTitledBorder(sound.getName()));

            // 再生・停止ボタンのパネル作成（FlowLayout）
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton playButton = new JButton("Play " + sound.getName());
            playButton.addActionListener(e -> sound.play());
            JButton stopButton = new JButton("Stop " + sound.getName());
            stopButton.addActionListener(e -> sound.stop());
            buttonPanel.add(playButton);
            buttonPanel.add(stopButton);

            // 音量調整用スライダーの作成
            JSlider volumeSlider = new JSlider(0, 100, (int) (sound.getVolume() * 100));
            volumeSlider.setMajorTickSpacing(20);
            volumeSlider.setPaintTicks(true);
            volumeSlider.setPaintLabels(true);
            volumeSlider.addChangeListener(e -> {
                int value = volumeSlider.getValue();
                sound.setVolume(value / 100.0f);
            });

            // パネルの配置：ボタンパネルを上部、スライダーを中央に
            soundPanel.add(buttonPanel, BorderLayout.NORTH);
            soundPanel.add(volumeSlider, BorderLayout.CENTER);

            // 各音源パネルをカテゴリパネルに追加（間に余白を入れる）
            categoryPanel.add(soundPanel);
            categoryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        return categoryPanel;
    }
}