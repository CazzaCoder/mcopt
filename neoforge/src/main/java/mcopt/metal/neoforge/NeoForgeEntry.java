package mcopt.metal.neoforge;

import mcopt.metal.PlatformCheck;
import mcopt.metal.Preload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge's counterpart of Fabric's preLaunch entrypoint: starts the class preload, where mcopt runs (PlatformCheck).
 * Later than on Fabric (mods are constructed after the game's bootstrap), but after Mixin is fully set up, so nothing gets
 * loaded ahead of its mixins.
 */
@Mod(value = "mcopt_metal", dist = Dist.CLIENT)
public final class NeoForgeEntry {
	static {
		mcopt.metal.Profile.apply(); // before any flag is read
	}

	public NeoForgeEntry() {
		if (PlatformCheck.enabled()) Preload.start();
	}
}
