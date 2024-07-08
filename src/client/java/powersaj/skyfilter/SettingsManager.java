package powersaj.skyfilter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class SettingsManager {
	public final Path settingsFilePath;
	public final Properties settings;

	public SettingsManager(String modsDirPath, String settingsFileName) {
		this.settingsFilePath = Paths.get(modsDirPath, settingsFileName);
		this.settings = new Properties();
		ensureSettingsFile();
	}

	public List<String> getBlockedTerms() {
        String blockedTerms = settings.getProperty("blockedTermsRegex", "");
        return Arrays.asList(blockedTerms.split(","));
    }

    public void setBlockedTerms(List<String> terms) {
        String blockedTerms = String.join(",", terms);
        settings.setProperty("blockedTermsRegex", blockedTerms);
        saveSettings();
    }

	public void ensureSettingsFile() {
		try {
			if (!Files.exists(settingsFilePath)) {
				Files.createDirectories(settingsFilePath.getParent());
				Files.createFile(settingsFilePath);
				System.out.println("Settings file created at: " + settingsFilePath);
			} else {
			}
		} catch (IOException e) {
			System.err.println("An error occurred while handling the settings file: " + e.getMessage());
		}
	}

	public void ensureDefaultSettings(Map<String, String> defaultSettings) {
		boolean modified = false;
		for (Map.Entry<String, String> entry : defaultSettings.entrySet()) {
			if (!settings.containsKey(entry.getKey())) {
				settings.setProperty(entry.getKey(), entry.getValue());
				modified = true;
			}
		}
		if (modified) {
			saveSettings();
		}
	}

	public void loadSettings() {
		try (FileInputStream fis = new FileInputStream(settingsFilePath.toFile())) {
			settings.load(fis);
		} catch (IOException e) {
			System.err.println("Failed to load settings: " + e.getMessage());
		}
	}

	public String getSetting(String key) {
		return settings.getProperty(key);
	}

	public void setSetting(String key, String value) {
		settings.setProperty(key, value);
		saveSettings();
	}

	private void saveSettings() {
		try (FileOutputStream fos = new FileOutputStream(settingsFilePath.toFile())) {
			settings.store(fos, null);
		} catch (IOException e) {
			System.err.println("Failed to save settings: " + e.getMessage());
		}
	}
}