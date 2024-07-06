package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("SkyFilter Client Side");
	}
}