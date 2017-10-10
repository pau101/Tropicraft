package net.tropicraft.core.common.biome.decorators;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.tropicraft.core.common.block.BlockCoral;
import net.tropicraft.core.common.enums.TropicraftCorals;
import net.tropicraft.core.common.worldgen.TCNoiseGen;
import net.tropicraft.core.common.worldgen.WorldGenCoral;
import net.tropicraft.core.common.worldgen.WorldGenSurfaceClump;
import net.tropicraft.core.registry.BlockRegistry;

public class BiomeDecoratorTropicsOcean extends BiomeDecoratorTropicraft {

	private static final TCNoiseGen coralGen = new WorldGenCoral(new Random(38745L));
	private static final WorldGenSurfaceClump coralReefGen = new WorldGenSurfaceClump(0.03f, 6, 
			state -> state.getBlock() == Blocks.SAND, 
			state -> true, // dummy 
			rand -> BlockRegistry.coral.getDefaultState().withProperty(BlockCoral.VARIANT, TropicraftCorals.VALUES[rand.nextInt(TropicraftCorals.VALUES.length)]), 
			false
		) 
	{
		
		@Override
		protected boolean canPlaceBlock(World world, BlockPos pos) {
			return super.canPlaceBlock(world, pos) && ((BlockCoral)BlockRegistry.coral).canBlockStay(world, pos);
		}
	};
	
	private static final WorldGenSurfaceClump seaweedGen = new WorldGenSurfaceClump(0.025f, 8, 
			state -> state.getBlock() == Blocks.SAND, 
			state -> state.getMaterial().isLiquid(),
			rand -> BlockRegistry.seaweed.getDefaultState(),
			true
		);

    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        if (this.decorating)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }
    
    public void genDecorations(Biome biome, World world, Random rand) {
		coralGen.generate(world, rand, chunkPos);
		coralReefGen.generate(world, rand, chunkPos);
		seaweedGen.generate(world, rand, chunkPos);
//		if (rand.nextInt(5) == 0) {
//			int x = randCoord(rand, chunkPos.getX(), 16) + 8;
//			int z = randCoord(rand, chunkPos.getZ(), 16) + 8;
//			BlockPos pos = new BlockPos(x, 0, z);
//			new WorldGenCoral().generate(world, rand, pos);
//		}
//		
//		if (rand.nextInt(8) == 0) {
//			int x = randCoord(rand, chunkPos.getX(), 16) + 8;
//			int z = randCoord(rand, chunkPos.getZ(), 16) + 8;
//			BlockPos pos = new BlockPos(x, 0, z);
//			new WorldGenSeaweed().generate(world, rand, pos);
//		}

//		if (ConfigGenRates.SHIPWRECK_CHANCE != 0 /*&& rand.nextInt(ConfigGenRates.SHIPWRECK_CHANCE) == 0*/) {
//			int i = randCoord(rand, chunkPos.getX(), 16);
//			int k = randCoord(rand, chunkPos.getZ(), 16);
//			new WorldGenSunkenShip(world, rand).generate(new BlockPos(i, getTerrainHeightAt(world, i, k), k));
//		}
    }

}
