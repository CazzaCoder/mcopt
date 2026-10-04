package mcopt.metal;

/**
 * Decides once whether mcopt runs in this game: only on the client of an Apple Silicon Mac with macOS 26 or later (the
 * native library is arm64-only). Anywhere else it switches itself off, with one log line saying why, and the game runs as
 * if it weren't installed, so the jar is safe in a modpack that's also played on other computers. Every mixin config
 * plugin, the profile and the loader entrypoints ask {@link #enabled()} first; when it is false no mixin applies and
 * nothing of the Metal backend or the natives loads. Reads only system properties (and the loader's side), so it is safe
 * in mixin config plugins. -Dmcopt.platformCheck.fake=linux (or x86_64, or a macOS version like 15.5) pretends to be that
 * platform, to test it.
 */
public final class PlatformCheck {
	private static final boolean ENABLED = decide();

	private PlatformCheck() {
	}

	public static boolean enabled() {
		return ENABLED;
	}

	private static boolean decide() {
		String os = System.getProperty("os.name", ""), arch = System.getProperty("os.arch", ""), version = System.getProperty("os.version", "");
		String fake = System.getProperty("mcopt.platformCheck.fake");
		if (fake != null) {
			if (fake.matches("[0-9.]+")) version = fake;
			else if (fake.equals("x86_64") || fake.equals("amd64")) arch = fake;
			else os = fake;
		}
		String problem = problem(os, arch, version);
		if (problem == null && Platform.get().isDedicatedServer()) problem = "It's client only, and this is a dedicated server.";
		if (problem == null) return true;
		System.out.println("[mcopt] mcopt Metal is switched off. " + problem + " The game runs as if it weren't installed.");
		return false;
	}

	private static String problem(String os, String arch, String version) {
		if (!os.startsWith("Mac")) return "It needs an Apple Silicon Mac (M1 or newer) with macOS 26 or later, and this computer runs " + os + ".";
		if (!arch.equals("aarch64") && !arch.equals("arm64")) {
			return "It needs an Apple Silicon Mac (M1 or newer) and an Apple Silicon (arm64) Java, and this Java is " + arch
				+ " (an Intel Mac, or an Intel Java running through Rosetta).";
		}
		int major;
		try {
			major = Integer.parseInt(version.split("\\.")[0]);
		} catch (NumberFormatException e) {
			return null; // unknown version string: let it try rather than block a working Mac
		}
		// A JDK built against an older SDK may see macOS 26 as "16.x" (Apple's compatibility numbering); there was no macOS 16.
		if (major < 26 && major != 16) return "It needs macOS 26 or later, and this Mac runs macOS " + version + ". Update macOS in System Settings > General > Software Update.";
		return null;
	}
}
