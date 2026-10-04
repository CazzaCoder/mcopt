package mcopt.metal.fabric;

import java.nio.file.Path;
import mcopt.metal.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class FabricPlatform implements Platform {
	@Override
	public Path gameDir() {
		return FabricLoader.getInstance().getGameDir();
	}

	@Override
	public boolean isModLoaded(String id) {
		return FabricLoader.getInstance().isModLoaded(id);
	}

	@Override
	public boolean isDedicatedServer() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
	}
}
