package com.example;

public interface Sound {
	void play();
	void stop();
	void setVolume(float volume); // 0.0～1.0の範囲で調整
	float getVolume();
	String getName();
}