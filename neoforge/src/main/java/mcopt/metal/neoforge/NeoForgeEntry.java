package mcopt.metal.neoforge;

import mcopt.metal.PlatformCheck;
import mcopt.metal.Preload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge's counterpart of Fabric's preLaunch entrypoint. Mods are constructed right after the game's bootstrap and
 * before Minecraft picks a graphics backend, so an unsupported Mac is stopped before any Metal class loads. The class
 * preload starts here too: later than on Fabric (the bootstrap has already run), but after Mixin is fully set up, so
 * nothing gets loaded ahead of its mixins.
 */
@Mod(value = "mcopt_metal", dist = Dist.CLIENT)
public final class NeoForgeEntry {
	static {
		mcopt.metal.Profile.apply(); // before any flag is read
	}

	public NeoForgeEntry() {
		PlatformCheck.run();
		Preload.start();
	}
}
