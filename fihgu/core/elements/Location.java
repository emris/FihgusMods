package fihgu.core.elements;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * contain a chunk location x,y,z
 * and a exact position posX,posY,posZ
 *
 * z and posZ represents height.
 */
public class Location 
{
	public int x,y,z;
	public double posX,posY,posZ;
	public int dimension;

	public Location(int x, int y, int z, int dimension)
	{
		this.x = x;
		this.y = y;
		this.z = z;

		this.posX = x;
		this.posY = y;
		this.posZ = z;

		this.dimension = dimension;
	}

	public Location(double posX, double posY, double posZ, int dimension)
	{
		init(posX, posY, posZ, dimension);
	}

	/**
	 * construct the location the player is currently at
	 */
	public Location(EntityPlayerMP player)
	{
		init(player.posX, player.posY, player.posZ, player.dimension);
	}

	/**
	 * construct a location from where the player is looking at.
	 */
	public Location(EntityPlayerMP player, String s)
	{
		init(player.posX, player.posY, player.posZ, player.dimension);
		if(s.equals("jump"))
		{
			MovingObjectPosition mop = getTargetBlock(player);
			if(mop != null)
			{
				if(mop.typeOfHit == EnumMovingObjectType.TILE)
				{
					double xx = mop.blockX;
					double yy = mop.blockY;
					double zz = mop.blockZ;

					if(mop.sideHit == 1) ++yy;
					if(mop.sideHit == 2) --zz;
					if(mop.sideHit == 3) ++zz;
					if(mop.sideHit == 4) --xx;
					if(mop.sideHit == 5) ++xx;

					init(xx, yy, zz, player.dimension);
				}
			}
		}
	}
	
	/**
	 * construct a location from another location's toString().
	 */
	public Location(String info)
	{
		String[] part = info.split("[,]");
		try
		{
			init(Double.parseDouble(part[0]), Double.parseDouble(part[1]), Double.parseDouble(part[2]), Integer.parseInt(part[3]));
		}
		catch(Exception e)
		{
			System.err.println("Exception on attemping to rebuild Location from String.");
			init(0,0,0,0);
		}
	}

	private void init(double posX, double posY, double posZ, int dimension)
	{
		this.x = round(posX);
		this.y = round(posY);
		this.z = round(posZ);

		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		this.dimension = dimension;
	}

	public void setSpawn(EntityPlayerMP player)
	{
		player.setSpawnChunk(new ChunkCoordinates(x,z,y), true);
	}

	/**
	 * floor then cast to int.
	 */
	private static int round(double pos)
	{
		return (int)Math.floor(pos);
	}

	public String toString()
	{
		return posX + "," + posY + "," + posZ + "," + dimension;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Location)
		{
			Location location = (Location)o;
			boolean equal = true;
			equal = equal && this.posX == location.posX;
			equal = equal && this.posY == location.posY;
			equal = equal && this.posZ == location.posZ;
			equal = equal && this.dimension == location.dimension;
			return equal;
		}
		return false;
	}
	
	private static MovingObjectPosition getTargetBlock(EntityPlayerMP P)
	{
		float v1 = 1.0F;
		double v2 = P.prevPosX + (P.posX - P.prevPosX) * v1;
		double v3 = P.prevPosY + (P.posY - P.prevPosY) * v1 + 1.62D - P.yOffset;
		double v4 = P.prevPosZ + (P.posZ - P.prevPosZ) * v1;
		Vec3 v5 = Vec3.createVectorHelper(v2, v3, v4);
		
		float v6 = P.prevRotationYaw + (P.rotationYaw - P.prevRotationYaw) * v1;
		float v7 = P.prevRotationPitch + (P.rotationPitch - P.prevRotationPitch) * v1;
		
		float v8 = MathHelper.cos(-v6 * 0.017453292F - (float)Math.PI);
		float v9 = MathHelper.sin(-v6 * 0.017453292F - (float)Math.PI);
		float v10 = -MathHelper.cos(-v7 * 0.017453292F);
		float v11 = MathHelper.sin(-v7 * 0.017453292F);
		float v12 = v8 * v10;
		float v13 = v9 * v10;
		double v14 = 160.0D; 
		Vec3 v15 = v5.addVector(v13 * v14, v11 * v14, v12 * v14);
		
		return P.worldObj.clip(v5, v15, true);
	}
}
