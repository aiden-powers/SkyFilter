package powersaj.skyfilter.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CancellationException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//net.minecraft.client.gui.hud.ChatHud
@Mixin(value = net.minecraft.client.gui.hud.ChatHud.class)
public class chatHudmix {
    @Unique
    private final List<Pattern> blockedTermsRegex = Arrays.asList(
            Pattern.compile("lowballing", Pattern.CASE_INSENSITIVE),
            Pattern.compile("/visit", Pattern.CASE_INSENSITIVE),
            Pattern.compile("/p", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(.)\\1{8,}", Pattern.CASE_INSENSITIVE), // Spam of one single letter 4 or more times
            Pattern.compile("visit", Pattern.CASE_INSENSITIVE),
            Pattern.compile("visit me", Pattern.CASE_INSENSITIVE),
            Pattern.compile("selling", Pattern.CASE_INSENSITIVE),
            Pattern.compile("party me", Pattern.CASE_INSENSITIVE),
            Pattern.compile("check my ah", Pattern.CASE_INSENSITIVE),
            Pattern.compile("my ah", Pattern.CASE_INSENSITIVE),
            Pattern.compile("in ah", Pattern.CASE_INSENSITIVE)
    );

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = true)
    private void injected(final net.minecraft.text.Text message, final net.minecraft.network.message.MessageSignatureData signatureData, final net.minecraft.client.gui.hud.MessageIndicator indicator, final CallbackInfo ci) {
        try {
            final String messageStr = message.getString().toLowerCase();
            for (final Pattern pattern : this.blockedTermsRegex) {
                final Matcher matcher = pattern.matcher(messageStr);
                if (matcher.find()) {
                    System.out.println(pattern + " BLOCKED. ORIGINAL MESSAGE: " + messageStr);
                    ci.cancel(); // Cancel the method execution if a blocked term is found
                    return;
                }
            }
        } catch (final CancellationException ignored) {
        }

    }
}