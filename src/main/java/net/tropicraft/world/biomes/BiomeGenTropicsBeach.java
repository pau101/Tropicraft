package net.tropicraft.world.biomes;

import java.util.Random;

import net.minecraft.world.World;
import net.tropicraft.world.worldgen.WorldGenTropicsTreasure;

public class BiomeGenTropicsBeach extends BiomeGenTropicraft {

	private static final int TREASURE_CHANCE = 150;
	
	public BiomeGenTropicsBeach(int biomeID) {
		super(biomeID);
	}
	
	@Override
	public void decorate(World world, Random rand, int x, int z) {
		if(rand.nextInt(TREASURE_CHANCE) == 0) {
			int i = randCoord(rand, x, 16);
			int k = randCoord(rand, z, 16);
			new WorldGenTropicsTreasure(world, rand).generate(i, getTerrainHeightAt(world, i, k), k);
		}
		
		super.decorate(world, rand, x, z);
	}

}
