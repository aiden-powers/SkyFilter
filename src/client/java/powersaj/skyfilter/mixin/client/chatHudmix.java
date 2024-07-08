package powersaj.skyfilter.mixin.client;

import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.spongepowered.asm.mixin.injection.callback.CancellationException;
import powersaj.skyfilter.SettingsManager;

@Mixin(value = net.minecraft.client.gui.hud.ChatHud.class)
public class chatHudmix {

    public chatHudmix() {
        loadBlockedTerms();
    }

    @Unique
    private List<Pattern> loadBlockedTerms() {
        String gameDir = System.getProperty("user.dir");
        String modsDirPath = gameDir + "/mods";
        String settingsFileName = "skyfilter_settings.txt";
        SettingsManager settingsManager = new SettingsManager(modsDirPath, settingsFileName);
        settingsManager.loadSettings();
        List<String> blockedTerms = settingsManager.getBlockedTerms();
        // compile the regex patterns
        List<Pattern> blockedTermsRegex = new ArrayList<>();
        for (String term : blockedTerms) {
            blockedTermsRegex.add(Pattern.compile(term));
        }
        return blockedTermsRegex;
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = true)
    private void injected(Text message, MessageSignatureData signatureData, MessageIndicator indicator, CallbackInfo ci) {
        try {
            List<Pattern> blockedTerms = loadBlockedTerms();
            // Check if the message contains any blocked terms
            for (Pattern pattern : blockedTerms) {
                String messageStr = String.valueOf(Text.of(message.getString().toLowerCase()));
                if (pattern.matcher(messageStr).find()) {
                    System.out.println(pattern + " BLOCKED. ORIGINAL MESSAGE: " + messageStr);
                    ci.cancel(); // Cancel the method execution if a blocked term is found
                    return;
                }
            }
        } catch (CancellationException e) {
	        throw new RuntimeException(e);
        }
    }
}