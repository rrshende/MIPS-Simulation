package Cache;

public class DcacheBlock {
	public boolean isValid;
	public boolean isDirty;
	public int tag;

	public DcacheBlock(){
		isValid = false;
		isDirty = false;
		tag=0;
	}
}
