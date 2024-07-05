package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ExampleModClient implements ClientModInitializer {
    /** public static void register() {
        MinecraftClient.getInstance().inGameHud.getChatHud()
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
            // Implement your logic here
            // For example, block messages containing "forbiddenWord"
            System.out.println(message.getContent());
            if (message.getContent().toString().toLowerCase().contains("lowballing")) {
                System.out.println(message.getContent());
                sender.sendMessage(Text.literal("MESSAGE BLOCKED CLIENTSIDE, 'lowballing'"), false);
                return false; // Block the message
            }
            return true; // Allow the message
        });
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            // Implement your logic here
            // For example, block messages containing "forbiddenWord"
            System.out.println(message.toString());
            if (message.toString().toLowerCase().contains("lowballing")) {
                System.out.println(message.toString());
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Message BLOCKED CLIENTSIDE, 'lowballing'"), false);
            }
        });
    }
     **/

	@Override
	public void onInitializeClient() {
	}
}