package mcopt.metal.fabric;

import mcopt.metal.PlatformCheck;
import mcopt.metal.Preload;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/** Fabric's preLaunch entrypoint: the class preload, where mcopt runs (PlatformCheck). */
public final class FabricPreLaunch implements PreLaunchEntrypoint {
	static {
		mcopt.metal.Profile.apply(); // before any flag is read
	}

	@Override
	public void onPreLaunch() {
		if (PlatformCheck.enabled()) Preload.start();
	}
}
