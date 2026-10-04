package mcopt.metal.fabric;

import mcopt.metal.PlatformCheck;
import mcopt.metal.Preload;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/** Fabric's preLaunch entrypoint: the platform check, then the class preload. */
public final class FabricPreLaunch implements PreLaunchEntrypoint {
	static {
		mcopt.metal.Profile.apply(); // before any flag is read
	}

	@Override
	public void onPreLaunch() {
		PlatformCheck.run();
		Preload.start();
	}
}
