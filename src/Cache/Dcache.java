package Cache;

import Main.ReadFile;

public class Dcache {

	private	DcacheBlock set[][];
	private int leasrRecent[];
	public int miss=0;
	public int hit=0;

	public Dcache() {
		set = new DcacheBlock[2][2];
		set[0][0] = new DcacheBlock();
		set[0][1] = new DcacheBlock();
		set[1][0] = new DcacheBlock();
		set[1][1] = new DcacheBlock();
		leasrRecent = new int[2];
	}

	public int getLCycles(int address1){
		boolean isBlockPresent = false;
		int cacheSetNumber = -1;

		int block  = (address1 >> 4)& 1;
		int tag = (address1 >> 5) & 3;

		int i = 0;
		for(i=0;i<2;i++)
		{
			if(set[i][block].isValid && set[i][block].tag == tag)
			{
				isBlockPresent = true;
				break;
			}

		}
		if(isBlockPresent)
		{
			cacheSetNumber = i;
			leasrRecent[block] = cacheSetNumber;
			hit++;
			return ReadFile.DcacheCC;//hit
		}
		else
		{
			for(i=0;i<2;i++)
			{
				if(!set[i][block].isValid)
				{
					isBlockPresent = true;
					break;
				}
			}

			if(isBlockPresent)
			{
				cacheSetNumber = i;
				leasrRecent[block] = cacheSetNumber;
				set[cacheSetNumber][block].isValid = true;
				set[cacheSetNumber][block].tag = tag;
				miss++;
				return 2 * (ReadFile.DcacheCC+ReadFile.memCC);//miss
			}
			else
			{
				cacheSetNumber = leasrRecent[block] ^ 1;
				leasrRecent[block] = cacheSetNumber;
				if(set[cacheSetNumber][block].isDirty)
				{//replace
					set[cacheSetNumber][block].isDirty = false;
					set[cacheSetNumber][block].tag = tag;
					set[cacheSetNumber][block].isValid = true;
					miss++;
					return 4*(ReadFile.DcacheCC+ReadFile.memCC);//miss

				}
				else
				{
					set[cacheSetNumber][block].isValid = true;
					set[cacheSetNumber][block].tag = tag;
					miss++;
					return 2*(ReadFile.DcacheCC+ReadFile.memCC);//miss
				}
			}
		}


	}
	public int getSCycles(int address1){
		boolean isBlockPresent = false;
		int cacheSetNumber = -1;
		int block  = (address1 >> 4)& 1;
		int tag = (address1 >> 5) & 3;

		int i = 0;
		for(i=0;i<2;i++)
		{
			if(set[i][block].isValid && set[i][block].tag == tag)
			{
				isBlockPresent = true;
				break;
			}

		}
		if(isBlockPresent)
		{
			cacheSetNumber = i;
			leasrRecent[block] = cacheSetNumber;
			//block pre dirt = true
			set[i][block].isDirty = true;
			hit++;
			return ReadFile.DcacheCC;//hit
		}
		else
		{
			for(i=0;i<2;i++)
			{
				if(!set[i][block].isValid)
				{
					isBlockPresent = true;
					break;
				}
			}

			if(isBlockPresent)
			{//invalid
				cacheSetNumber = i;
				leasrRecent[block] = cacheSetNumber;
				set[cacheSetNumber][block].isValid = true;
				set[cacheSetNumber][block].tag = tag;
				set[i][block].isDirty = true;
				miss++;
				return 2 * (ReadFile.DcacheCC+ReadFile.memCC);
			}
			else
			{
				cacheSetNumber = leasrRecent[block] ^ 1;
				leasrRecent[block] = cacheSetNumber;
				if(set[cacheSetNumber][block].isDirty)
				{
					set[cacheSetNumber][block].isDirty = true;
					set[cacheSetNumber][block].tag = tag;
					set[cacheSetNumber][block].isValid = true;
					miss++;
					return 4*(ReadFile.DcacheCC+ReadFile.memCC);

				}
				else
				{
					set[cacheSetNumber][block].isValid = true;
					set[cacheSetNumber][block].tag = tag;
					set[i][block].isDirty = true;
					miss++;
					return 2*(ReadFile.DcacheCC+ReadFile.memCC);
				}
			}
		}

	}
}
