package com.example.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

//net.minecraft.client.gui.hud.ChatHud
@Mixin(targets = "net.minecraft.client.gui.hud.ChatHud")
public class chatHud {
    @Unique
    private final List<String> blockedTerms = Arrays.asList("lowballing", "/visit", "/p");
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = true)
    private void injected(net.minecraft.text.Text message, final net.minecraft.network.message.MessageSignatureData signatureData, final net.minecraft.client.gui.hud.MessageIndicator indicator, final CallbackInfo ci) {
        // Assuming textToString method exists and converts Text to String
        final String messageStr = this.textToString(message).toLowerCase();
        for (final String term : this.blockedTerms) {
            if (messageStr.contains(term)) {
                System.out.println(term + " term blocked.");
                message = net.minecraft.text.Text.of("[SkyFilter] Blocked Message");
                ci.cancel(); // Cancel the method execution if a blocked term is found
                return;
            }
        }
        System.out.println("no block detected.");
    }

    // Placeholder for the textToString method - you'll need to implement this based on your setup
    @Unique
    private String textToString(net.minecraft.text.Text text) {
        // Convert Text to String here
        return text.toString();
    }
}