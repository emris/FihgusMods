package fihgu.core.transformers.containers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class FBlock extends Block
{
	public FBlock(int par1, Material par2Material) 
	{
		super(par1, par2Material);
	}

	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		fihgu.core.events.BlockExplodedEvent event = new fihgu.core.events.BlockExplodedEvent(new fihgu.core.elements.Location(x,y,z,world.provider.dimensionId),explosion);

		if(!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
		{
			world.setBlockToAir(x, y, z);
			super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
		}
	}
}
