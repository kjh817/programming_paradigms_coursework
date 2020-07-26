import java.util.Comparator;

class SpriteComparator implements Comparator<Sprite>
{
	public int compare(Sprite a, Sprite b)
	{
		if(a.x < b.x)
			return -1;
		else if(a.x > b.x)
			return 1;
		else
			return 0;
	}

	public boolean equals(Object obj)
	{
		return false;
	}
}
