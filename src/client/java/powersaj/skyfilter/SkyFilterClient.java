package powersaj.skyfilter;

import net.fabricmc.api.ClientModInitializer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkyFilterClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("SkyFilter Client Side");

		String gameDir = System.getProperty("user.dir");
		String modsDirPath = gameDir + "/mods";
		String settingsFileName = "skyfilter_settings.txt";
		SettingsManager settingsManager = new SettingsManager(modsDirPath, settingsFileName);
		settingsManager.loadSettings();

		// Example usage
		String regexterms = settingsManager.getSetting("blockedTermsRegex");
		if (regexterms == null) {
			settingsManager.setSetting("blockedTermsRegex", "(/visit|/p)|(visit me)|(lowballing|lowballin|lowballif)|(buying)|(my ah)|(selling)");
		}

		// Inside your SkyFilterClient class, after loading settings
		Map<String, String> defaultSettings = new HashMap<>();
		defaultSettings.put("blockedTermsRegex", "(/visit|/p)|(visit me)|(lowballing|lowballin|lowballif)|(buying)|(my ah)|(selling)");
		settingsManager.ensureDefaultSettings(defaultSettings);
		
		// Assuming the mods folder is directly under the game directory
		Path modsDir = Paths.get(gameDir, "mods");
		Path settingsFilePath = modsDir.resolve("skyfilter_settings.txt");
		
		try {
			if (!Files.exists(settingsFilePath)) {
				Files.createDirectories(modsDir); // Ensure the mods directory exists
				Files.createFile(settingsFilePath);
				System.out.println("Settings file created at: " + settingsFilePath);
			} else {
				System.out.println("Settings file already exists.");
			}
		} catch (IOException e) {
			System.err.println("An error occurred while handling the settings file: " + e.getMessage());
		}
	}
}