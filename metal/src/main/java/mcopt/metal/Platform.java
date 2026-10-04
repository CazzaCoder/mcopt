package mcopt.metal;

import java.nio.file.Path;

/**
 * What mcopt needs from the mod loader. Each loader's jar carries exactly one implementation, found by name on first
 * use: mcopt.metal.fabric.FabricPlatform (metal/) or mcopt.metal.neoforge.NeoForgePlatform (neoforge/).
 */
public interface Platform {
	Path gameDir();

	boolean isModLoaded(String id);

	/**
	 * A dedicated server, where mcopt (client only) switches itself off (PlatformCheck). Fabric never loads it there
	 * ("environment": "client"); NeoForge has no such switch.
	 */
	boolean isDedicatedServer();

	static Platform get() {
		return Holder.INSTANCE;
	}

	final class Holder {
		private static final String[] IMPLEMENTATIONS = {"mcopt.metal.fabric.FabricPlatform", "mcopt.metal.neoforge.NeoForgePlatform"};
		static final Platform INSTANCE = load();

		private Holder() {
		}

		private static Platform load() {
			for (String name : IMPLEMENTATIONS) {
				Class<?> type;
				try {
					type = Class.forName(name, true, Platform.class.getClassLoader());
				} catch (ClassNotFoundException absent) {
					continue; // the other loader's jar
				}
				try {
					return (Platform) type.getDeclaredConstructor().newInstance();
				} catch (ReflectiveOperationException e) {
					throw new IllegalStateException("mcopt: can't create " + name, e);
				}
			}
			throw new IllegalStateException("mcopt: no Platform implementation in this jar");
		}
	}
}
