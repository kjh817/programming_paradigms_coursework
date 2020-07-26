import java.util.Comparator;

class TubeComparator implements Comparator<Tube>
{
	public int compare(Tube a, Tube b)
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
